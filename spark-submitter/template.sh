#!/bin/bash

cd /usr/src/app
ls -a
echo "test"
#SPARK_APPLICATION_JAR_NAME is from bde2020/spark-submit:2.4.4-hadoop2.7 image
#SPARK_APPLICATION_JAR_LOCATION is from dockerfile
cp ./target/${SPARK_APPLICATION_JAR_NAME} ${SPARK_APPLICATION_JAR_LOCATION}

#Script from bde2020/spark-submit:2.4.4-hadoop2.7 image
sh /submit.sh