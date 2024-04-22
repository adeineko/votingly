# resource "google_compute_managed_ssl_certificate" "lb_default" {
#   #   provider = google-beta
#   name = "myservice-ssl-cert"

#   managed {
#     domains = ["example.com"]
#   }
# }

# resource "google_compute_target_https_proxy" "lb_default" {
#   #   provider = google-beta
#   name    = "myservice-https-proxy"
#   url_map = google_compute_url_map.lb_default.id
#   ssl_certificates = [
#     google_compute_managed_ssl_certificate.lb_default.name
#   ]
#   depends_on = [
#     google_compute_managed_ssl_certificate.lb_default
#   ]
# }

# resource "google_compute_instance_template" "default" {
#   name = "lb-backend-template"
#   disk {
#     auto_delete  = true
#     boot         = true
#     device_name  = "persistent-disk-0"
#     mode         = "READ_WRITE"
#     source_image = "projects/debian-cloud/global/images/family/debian-11"
#     type         = "PERSISTENT"
#   }
#   labels = {
#     managed-by-cnrm = "true"
#   }
#   machine_type = "n1-standard-1"
#   metadata = {
#     startup-script = "#! /bin/bash\n     sudo apt-get update\n     sudo apt-get install apache2 -y\n     sudo a2ensite default-ssl\n     sudo a2enmod ssl\n     vm_hostname=\"$(curl -H \"Metadata-Flavor:Google\" \\\n   http://169.254.169.254/computeMetadata/v1/instance/name)\"\n   sudo echo \"Page served from: $vm_hostname\" | \\\n   tee /var/www/html/index.html\n   sudo systemctl restart apache2"
#   }
#   network_interface {
#     access_config {
#       network_tier = "PREMIUM"
#     }
#     network    = "global/networks/default"
#     subnetwork = "regions/us-east1/subnetworks/default"
#   }
#   region = "us-east1"
#   scheduling {
#     automatic_restart   = true
#     on_host_maintenance = "MIGRATE"
#     provisioning_model  = "STANDARD"
#   }
#   service_account {
#     email  = "default"
#     scopes = ["https://www.googleapis.com/auth/devstorage.read_only", "https://www.googleapis.com/auth/logging.write", "https://www.googleapis.com/auth/monitoring.write", "https://www.googleapis.com/auth/pubsub", "https://www.googleapis.com/auth/service.management.readonly", "https://www.googleapis.com/auth/servicecontrol", "https://www.googleapis.com/auth/trace.append"]
#   }
#   tags = ["allow-health-check"]
# }

# resource "google_compute_instance_group_manager" "default" {
#   name = "lb-backend-example"
#   zone = "us-east1-b"
#   named_port {
#     name = "http"
#     port = 80
#   }
#   version {
#     instance_template = google_compute_instance_template.default.id
#     name              = "primary"
#   }
#   base_instance_name = "vm"
#   target_size        = 2
# }

# resource "google_compute_firewall" "default" {
#   name          = "fw-allow-health-check"
#   direction     = "INGRESS"
#   network       = "global/networks/default"
#   priority      = 1000
#   source_ranges = ["130.211.0.0/22", "35.191.0.0/16"]
#   target_tags   = ["allow-health-check"]
#   allow {
#     ports    = ["80"]
#     protocol = "tcp"
#   }
# }