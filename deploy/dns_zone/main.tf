# Create a DNS zone for managing our domain votingly.tech
resource "google_dns_managed_zone" "votingly-tech" {
  name        = "votingly-tech"
  dns_name    = "votingly.tech."
  description = "Votingly public DNS zone"
  # Make public to the internet
  visibility = "public"

  # Domain Name System Security Extensions (DNSSEC) is a feature of the Domain Name System (DNS) that authenticates responses to domain name lookups
  # https://cloud.google.com/dns/docs/dnssec
  dnssec_config {
    state = "on"
  }

  # Enable logging for the DNS zone (paid)
  cloud_logging_config {
    enable_logging = true
  }

  # Comment this block to destroy the DNS zone
  # DO NOT DESTROY if you don't know what you're doing
  lifecycle {
    prevent_destroy = true
  }
}
