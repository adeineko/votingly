# Network configuration for the Votingly application.
resource "google_compute_network" "votingly-network" {
  name = "votingly-network"
  # REQUIRED: Disabling auto-creation of subnetworks (regions)
  auto_create_subnetworks = false
  # auto_create_subnetworks = true
}

# Subnetwork configuration for the Votingly application for Belgium region
resource "google_compute_subnetwork" "votingly-subnet" {
  name    = "votingly-subnet"
  network = google_compute_network.votingly-network.id

  # Specify some IP range to be used by the subnet
  ip_cidr_range = "10.0.0.0/24"

  # REQUIRED: Belgium region
  region = "europe-west1"
}

# Firewall rule: Allow access via HTTP and HTTPS from the internet
resource "google_compute_firewall" "votingly-firewall-web" {
  name    = "votingly-firewall-web"
  network = google_compute_network.votingly-network.self_link

  # IP range of the internet
  source_ranges = ["0.0.0.0/0"]

  direction   = "INGRESS"
  source_tags = ["web"]

  allow {
    protocol = "tcp"
    ports    = ["80", "443"]
  }

  # TODO: Not sure if other protocols needed
  # allow {
  #   protocol = "udp"
  #   ports    = ["80", "443"]
  # }

  # allow {
  #   protocol = "sctp"
  #   ports    = ["80", "443"]
  # }

  # Enable logging for debugging
  log_config {
    metadata = "INCLUDE_ALL_METADATA"
  }
}

# Firewall rule: Allow access via SSH from IP ranges
resource "google_compute_firewall" "votingly-firewall-ssh" {
  name    = "votingly-firewall-ssh"
  network = google_compute_network.votingly-network.self_link

  # IP range of: GCloud integrated terminal, ross development server
  source_ranges = ["35.235.240.0/20", "94.130.10.128/25"]

  direction   = "INGRESS"
  source_tags = ["ssh"]

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }
}
