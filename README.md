# Int 4 Team 9 Project

### Team
- Sofiia Hmyria 
(sofiia.hmyria@student.kdg.be)
- Gaëlle Clement
(gaelle.clement@student.kdg.be)
- Anna Deineko
(anna.deineko@student.kdg.be)
- Nathan van Nieuwstadt
(nathan.vannieuwstadt@student.kdg.be)
- Rostislav Rucka
(rostislav.rucka@student.kdg.be)

## Build and run instructions

### Local
```
./gradlew clean build
docker compose up -d
java -jar -Dspring.profiles.active=dev build/libs/Integration4-17.0.10.jar
```
Access at localhost:8080

### Dev Container
```
./gradlew clean -Dspring.profiles.active=testcontainer bootJar
java -jar -Dspring.profiles.active=devcontainer build/libs/Integration4-17.0.10.jar
```

### Dockerize
```
docker build --tag=int4t9:latest .
docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=devcontainer" int4t9:latest

# To run on same network in dev container use --network=int4t9_default
```

## Infrastructure as Code

### [Documentation](https://docs.google.com/document/d/1ln7VSWr5J5QCrjL_6ltrYKLsqeg2uPohHJbpUnN5FcE/edit?usp=sharing)

### Instructions
- Open this project
- In [GCP IAM](https://console.cloud.google.com/iam-admin/serviceaccounts) - Create your service account with 'Compute Admin', ‘DNS Admin’, ‘Service Account Admin’, ‘Service Account User’ permission. Then put its key in deploy/.creds and rename to 'gcloud_sa_compute_admin.json'
- [Install Terraform](https://developer.hashicorp.com/terraform/install) (or see [VScode dev containers](https://code.visualstudio.com/docs/devcontainers/containers#_quick-start-open-an-existing-folder-in-a-container) on how to open our project in a container)
```
cd deploy/prod

# Initialize (only first time)
terraform init

# Get a preview of changes that Terraform plans to make to infrastructure
terraform plan

# Execute the actions proposed in Terraform plan
terraform apply

# Destroy all remote objects managed by Terraform
terraform destroy
```

## Dev instructions

```
npm init
docker compose up -d
```

Run Tests (specify spring active profile)
```
./gradlew -PspringProfilesActiveTests=<profile_name> check
```

Continuously rebuild on code changes
```
./gradlew -t classes
```