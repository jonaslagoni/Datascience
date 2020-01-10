#!/bin/bash
docker-compose build spark-submitter
docker-compose stop spark-submitter
docker-compose up -d spark-submitter