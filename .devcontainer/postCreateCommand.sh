#!/bin/sh

whoami
java -version

cd /workspace

# sudo chown -R vscode:vscode .
# sudo chown -R vscode:vscode ~/.ssh
# chmod  0700 ~/.ssh; chmod 600 ~/.ssh/*
sudo su -c "alias ssha='eval $(ssh-agent) && ssh-add /home/vscode/.ssh/id_rsa'" vscode
# source ~/.bashrc
sudo su -c "ssha" vscode
