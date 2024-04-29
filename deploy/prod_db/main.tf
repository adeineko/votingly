resource "google_sql_database" "votingly-db" {
  name     = "votingly-db"
  instance = google_sql_database_instance.instance.name
}

# See versions at https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/sql_database_instance#database_version
resource "google_sql_database_instance" "votingly-db-instance" {
  name             = "votingly-db-instance"
  region           = "europe-west1"
  database_version = "POSTGRES_15"
  settings {
    tier = "db-f1-micro"
  }

  deletion_protection = "true"
}