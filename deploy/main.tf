resource "google_compute_network" "app_network" {
  name = "app-network"
  // REQUIRED: IP Address  - ephemeral → use dynamic DNS
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "app_test_subnet" {
  name          = "app-test-subnet"
  network       = google_compute_network.app_network.id
  ip_cidr_range = "10.0.0.0/24"
  region        = "europe-west1"
}

resource "google_compute_firewall" "app_test_firewall" {
  name    = "app-test-firewall"
  network = google_compute_network.app_network.self_link

  source_ranges = ["0.0.0.0/0"]
  direction     = "INGRESS"
  source_tags   = ["web"]

  allow {
    protocol = "tcp"
    ports    = ["80", "443"]
  }

  allow {
    protocol = "udp"
    ports    = ["80", "443"]
  }

  allow {
    protocol = "sctp"
    ports    = ["80", "443"]
  }
}

// REQUIRED: The test environment of your application will be deployed on google cloud.
resource "google_compute_instance" "app_test_vm" {
  name         = "app-test-vm"
  machine_type = "e2-medium"
  // REQUIRED: You can assume your test users will mainly be located in Belgium. GCloud region europe-west1
  zone = "europe-west1-b"

  tags = ["int4t9", "test", "web"]
  # allow_stopping_for_update = true

  // REQUIRED: Server image: some kind of Linux
  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-11"
    }
  }

  // Local SSD disk
  # scratch_disk {
  #   interface = "NVME"
  # }


  network_interface {
    network    = google_compute_network.app_network.self_link
    subnetwork = google_compute_subnetwork.app_test_subnet.self_link
    // REQUIRED: IP Address  - ephemeral → use dynamic DNS
    access_config {
      // Ephemeral public IP
    }
  }

  # metadata = {
  #   foo = "bar"
  # }

  # metadata_startup_script = "echo hi > /test.txt"

  # service_account {
  #   # Google recommends custom service accounts that have cloud-platform scope and permissions granted via IAM Roles.
  #   email  = google_service_account.default.email
  #   scopes = ["cloud-platform"]
  # }
}
