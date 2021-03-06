#!/bin/bash
npm install -g asyncapi-generator
docker-compose build --parallel
docker-compose down

if [ ! -f "./.env" ]; then
echo "enter your local ip (192.168.0.11)"
read ip
if [ -z "${ip}" ]; then
	ip="192.168.0.11"
fi
cat >.env <<EOL
DOCKER_MACHINE_IP=${ip}
EOL
fi
docker-compose up -d