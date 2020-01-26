#!/bin/bash
docker_compose="docker-compose -f ./docker-compose-spark.yml -f ./docker-compose-kafka.yml -f ./docker-compose-services.yml"
eval "$docker_compose build --parallel"
eval "$docker_compose down"

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
eval "$docker_compose up -d --scale spark-worker=3"