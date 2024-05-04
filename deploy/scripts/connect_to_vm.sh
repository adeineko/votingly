#!/bin/bash

# Pre-requisites:
# gcloud installed and authenticated
# your ip range is allowed in firewall ssh (you can temporarily add 0.0.0.0/0 for all internet if you don't know your ip range)
gcloud compute ssh "votingly-testing-vm" --zone "europe-west1-b" --project "int4t9"
