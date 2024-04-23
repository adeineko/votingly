#!/bin/bash

# Automated script to run on VM boot

# Install Docker Compose
# https://github.com/docker/compose/issues/10463
curl -sSL \
    https://github.com/docker/compose/releases/download/v2.23.3/docker-compose-linux-x86_64 \
    -o /var/lib/google/docker-compose
chmod o+x /var/lib/google/docker-compose
mkdir -p ~/.docker/cli-plugins
ln -sf /var/lib/google/docker-compose ~/.docker/cli-plugins/docker-compose
# docker compose version

# Install HTTPS Portal
mkdir /tmp/https-portal
mkdir /tmp/https-portal/{log,logrotate}
curl https://gitlab.com/kdg-ti/integration-4/2023-2024/team-9/int4t9/-/snippets/3702731/raw/main/docker-compose.yml \
    -o /tmp/https-portal/docker-compose.yml
docker compose up -d -f /tmp/https-portal/docker-compose.yml
