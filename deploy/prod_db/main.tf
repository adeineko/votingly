data "google_compute_instance" "votingly-testing-vm" {
  name = "votingly-testing-vm"
}

data "google_compute_network" "votingly-network" {
  name = "votingly-network"
}

resource "google_sql_database" "votingly-db" {
  name     = "votingly-db"
  instance = google_sql_database_instance.votingly-db-instance.name
}

resource "google_compute_global_address" "private-ip-db" {
  name          = "private-ip-db"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = data.google_compute_network.votingly-network.id
}

resource "google_service_networking_connection" "private_vpc_connection" {
  network                 = data.google_compute_network.votingly-network.id
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.private-ip-db.name]
}

# See versions at https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/sql_database_instance#database_version
resource "google_sql_database_instance" "votingly-db-instance" {
  name             = "votingly-db-instance"
  region           = "europe-west1"
  database_version = "POSTGRES_15"

  depends_on = [google_service_networking_connection.private_vpc_connection]
  settings {
    tier = "db-f1-micro"
    # HA Regional disabled
    availability_type = "ZONAL"
    ip_configuration {
      ipv4_enabled                                  = false
      private_network                               = data.google_compute_network.votingly-network.id
      enable_private_path_for_google_cloud_services = true
      # Allow access from the VM
      # authorized_networks {
      #   name  = "votingly-network"
      #   value = data.google_compute_instance.votingly-testing-vm.network_interface.0.access_config.0.nat_ip
      # }
      # psc_config {
      #   psc_enabled               = true
      #   allowed_consumer_projects = ["int4t9"]
      # }
    }
    # Enable automatic backups and point-in-time recovery
    backup_configuration {
      enabled                        = true
      location                       = "europe-west1"
      point_in_time_recovery_enabled = true
      transaction_log_retention_days = 2
      backup_retention_settings {
        retention_unit   = "COUNT"
        retained_backups = 2
      }
    }
  }

  deletion_protection = true
}

resource "google_sql_user" "db-user" {
  name     = var.db_user
  instance = google_sql_database_instance.votingly-db-instance.name
  password = var.db_password
}
