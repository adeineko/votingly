#!/bin/bash

# Run to get DNSSEC record for setup with registrar
# https://cloud.google.com/dns/docs/registrars

ZONE_NAME="votingly-tech"
DELIM="------------------------------------------------------------------------"

gcloud dns dns-keys list --zone=$ZONE_NAME
gcloud dns dns-keys describe 0 --zone=$ZONE_NAME
