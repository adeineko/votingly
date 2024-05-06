#!/bin/bash

# Automated script to run on VM boot

if [ -f /home/admin/docker-compose.yml ]; then
    # Rerun docker compose
    sudo su -c "cd ~ &&
    docker compose down &&
    docker compose up -d" admin
fi
