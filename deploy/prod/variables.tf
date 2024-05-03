variable "vm_state" {
  type    = string
  default = "RUNNING"
}

variable "vm_ssh_pub_key" {
  type    = string
  default = "../.creds/vm_key.pub"
}

variable "gcp_sa_credentials" {
  type    = string
  default = "../.creds/gcloud_sa_compute_admin.json"
}
