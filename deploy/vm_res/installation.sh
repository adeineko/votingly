#!/bin/bash

# Automated script to run on CICD provision infrastructure job

# useradd admin
sudo usermod -aG docker admin

# Install Docker Compose
# https://github.com/docker/compose/issues/10463
sudo curl -sSL \
    https://github.com/docker/compose/releases/download/v2.23.3/docker-compose-linux-x86_64 \
    -o /var/lib/google/docker-compose
sudo chmod o+x /var/lib/google/docker-compose
mkdir -p ~/.docker/cli-plugins
ln -sf /var/lib/google/docker-compose ~/.docker/cli-plugins/docker-compose
# docker compose version

# Login to gitlab registry
docker login registry.gitlab.com -u int4t9-deploy-token --password-stdin <<< $(docker run google/cloud-sdk:alpine gcloud secrets versions access latest --secret="GITLAB_DEPLOY_TOKEN" --project int4t9)

# Run docker compose with HTTPS Portal and Web App
mkdir ~/https-portal
mkdir ~/https-portal/{log,logrotate}
cd ~
docker compose up -d

# git clone https://gist.github.com/4fcd04db36e8d6a58300c55263db3285.git ~/votingly &&
