#!/bin/bash
docker_compose="docker-compose -f ./docker-compose-spark.yml -f ./docker-compose-kafka.yml -f ./docker-compose-services.yml"
eval "$docker_compose $1"