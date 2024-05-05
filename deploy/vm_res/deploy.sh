#!/bin/bash

# Automated script to run on CICD deployment job

# Login to gitlab registry
# sudo su -c "docker login registry.gitlab.com -u int4t9-deploy-token --password-stdin <<< $(docker run -ti --name gcloud-config gcr.io/google.com/cloudsdktool/google-cloud-cli gcloud secrets versions access latest --secret="GITLAB_DEPLOY_TOKEN")" admin

# Rerun docker compose to pull the latest image
cd ~
docker compose down
docker compose up -d
