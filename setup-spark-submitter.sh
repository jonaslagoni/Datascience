#!/bin/bash
compose_command="docker-compose -f ./docker-compose-spark.yml -f ./docker-compose-kafka.yml -f ./docker-compose-services.yml"
eval "$compose_command build spark-submitter"
eval "$compose_command stop spark-submitter"
eval "$compose_command up -d spark-submitter"