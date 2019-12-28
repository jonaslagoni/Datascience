#!/bin/bash
docker run --rm -e HOST_IP=$1 -e ZK=$2 -i -t wurstmeister/kafka //bin//bash
