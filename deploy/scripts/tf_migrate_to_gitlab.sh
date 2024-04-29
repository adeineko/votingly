#!/bin/bash

# Script to migrate terraform state to gitlab
# https://docs.gitlab.com/ee/user/infrastructure/iac/terraform_state.html#migrate-to-a-gitlab-managed-terraform-state

TF_DIR=$(pwd)

cd $(dirname "$0")
source $(pwd)/../../.env.local

PROJECT_ID="54600728"
TF_USERNAME=$GL_USER
TF_PASSWORD=$GL_TOKEN_ALL

TF_ADDRESS_OLD="https://gitlab.com/api/v4/projects/${PROJECT_ID}/terraform/state/old-state-name"
TF_ADDRESS_NEW="https://gitlab.com/api/v4/projects/${PROJECT_ID}/terraform/state/votingly-tf"

# echo $TF_USERNAME
# echo $TF_PASSWORD

cd $TF_DIR

if [[ $1 == "setup" ]];then
    # terraform init
    terraform init \
        -backend-config=address=${TF_ADDRESS_OLD} \
        -backend-config=lock_address=${TF_ADDRESS_OLD}/lock \
        -backend-config=unlock_address=${TF_ADDRESS_OLD}/lock \
        -backend-config=username=${TF_USERNAME} \
        -backend-config=password=${TF_PASSWORD} \
        -backend-config=lock_method=POST \
        -backend-config=unlock_method=DELETE \
        -backend-config=retry_wait_min=5 \
        --reconfigure
elif [[ $1 == "migrate" ]];then
    terraform init \
        -migrate-state \
        -backend-config=address=${TF_ADDRESS_NEW} \
        -backend-config=lock_address=${TF_ADDRESS_NEW}/lock \
        -backend-config=unlock_address=${TF_ADDRESS_NEW}/lock \
        -backend-config=username=${TF_USERNAME} \
        -backend-config=password=${TF_PASSWORD} \
        -backend-config=lock_method=POST \
        -backend-config=unlock_method=DELETE \
        -backend-config=retry_wait_min=5
    terraform plan
else
    echo "Invalid argument!"
    echo "Usage: ./tf_migrate_to_gitlab.sh setup|migrate"
fi
