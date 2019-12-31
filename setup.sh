#!/bin/bash
npm install -g asyncapi-generator
docker-compose build --parallel
echo "enter your local ip (192.168.0.11)"
read ip
cat >.env <<EOL
DOCKER_MACHINE_IP=${ip}
EOL
docker-compose up -d