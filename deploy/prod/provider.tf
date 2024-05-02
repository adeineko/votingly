terraform {
  # required_providers {
  #   google = {
  #     source  = "hashicorp/google"
  #     version = "5.25.0"
  #   }
  # }
  backend "http" {
  }
}

provider "google" {
  # Configuration options
  project     = "int4t9"
  region      = "europe-west1"
  zone        = "europe-west1-b"
  credentials = file("../.creds/gcloud_sa_compute_admin.json")
}
