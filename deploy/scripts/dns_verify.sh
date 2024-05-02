#!/bin/bash

# Run to verify NS, DNS and A record for our domain
# https://cloud.google.com/dns/docs/update-name-servers

ZONE_NAME="votingly-tech"
DOMAIN="votingly.tech."
DELIM="------------------------------------------------------------------------"

# List Cloud DNS name servers
gcloud dns managed-zones describe $ZONE_NAME
echo $DELIM

# Verify that the name servers for the domain match the name servers listed in the Cloud DNS zone.
dig -t NS $DOMAIN
echo $DELIM

# Verify that the domain is correctly pointing to IP address.
nslookup votingly.tech
