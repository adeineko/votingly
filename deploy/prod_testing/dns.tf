# Fetch the DNS zone for the environment
data "google_dns_managed_zone" "env_dns_zone" {
  name = "votingly-tech"
}

# Create a DNS record to register VM's ip address in DNS
resource "google_dns_record_set" "votingly-testing-vm-dns-record" {
  name         = data.google_dns_managed_zone.env_dns_zone.dns_name
  managed_zone = data.google_dns_managed_zone.env_dns_zone.name
  type         = "A"
  ttl          = 300

  rrdatas = [
    google_compute_instance.votingly-testing-vm.network_interface[0].access_config[0].nat_ip
  ]
}
