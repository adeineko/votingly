# VM Compute Instance configuration for the Votingly application testing environment.
resource "google_compute_instance" "votingly-testing-vm" {
  name = "votingly-testing-vm"

  # GCP Machine Types - VM type with specific CPU, RAM
  # https://cloud.google.com/compute/docs/general-purpose-machines#e2_machine_types
  # machine_type = "e2-medium"
  machine_type = "e2-small"

  # REQUIRED: You can assume your test users will mainly be located in Belgium. GCloud region europe-west1
  zone = "europe-west1-b"

  tags                      = ["web", "ssh"]
  allow_stopping_for_update = true

  # Configuration of disk with OS
  boot_disk {
    initialize_params {
      # REQUIRED: Server image: some kind of Linux
      # Using Container-Optimized OS (COS) image from Google
      # https://cloud.google.com/container-optimized-os/docs
      image = "cos-cloud/cos-stable"
    }
  }

  # Network configuration
  network_interface {
    network    = google_compute_network.votingly-network.self_link
    subnetwork = google_compute_subnetwork.votingly-subnet.self_link
    # REQUIRED: IP Address  - ephemeral → use dynamic DNS
    access_config {
      // Ephemeral public IP - requires this to be blank
    }
  }

  # Adding SSH keys
  metadata = {
    #   "user-data" = file("config/cloud-init.yml")
    ssh-keys = "admin:${file("../.creds/vm_key.pub")}"
  }

  # Script to run on every boot
  metadata_startup_script = file("../res/startup_cos_https_portal.sh")
}
