variable "project_root" {
  type    = string
  default = "../../"
}

variable "vm_state" {
  type    = string
  default = "RUNNING"
}

variable "vm_ssh_pub_key" {
  type    = string
  default = "deploy/.creds/vm_key.pub"
}

variable "gcp_sa_credentials" {
  type    = string
  default = "deploy/.creds/gcloud_sa_compute_admin.json"
}
