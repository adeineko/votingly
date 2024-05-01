#!/bin/sh

whoami
java -version

cd /workspace

# sudo chown -R vscode:vscode .
# sudo chown -R vscode:vscode ~/.ssh
# chmod  0700 ~/.ssh; chmod 600 ~/.ssh/*

mkdir -p ~/.gradle
echo springProfilesActiveTests=testcontainer >>  ~/.gradle/gradle.properties
