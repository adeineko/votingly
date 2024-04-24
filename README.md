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

### Build and run instructions (cmd)

Local
```
./gradlew clean bootJar
java -jar -Dspring.profiles.active=dev build/libs/Integration4-17.0.10.jar
```
Dev Container
```
./gradlew clean -Dspring.profiles.active=testcontainer bootJar
java -jar -Dspring.profiles.active=devcontainer build/libs/Integration4-17.0.10.jar
```

```
docker build --tag=int4t9:latest .
docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=devcontainer" --network=int4t9_default int4t9:latest
```

Terraform Usage and info:
https://gitlab.com/kdg-ti/integration-4/2023-2024/team-9/int4t9/-/wikis/home/Infrastructure-as-Code-(Iac)-Terraform-to-Google-Cloud

### Dev instructions

```
npm init
```