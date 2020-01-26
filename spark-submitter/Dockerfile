FROM bde2020/spark-java-template:latest


ENV SPARK_APPLICATION_JAR_NAME spark-submitter-1.0.0-with-dependencies
ENV SPARK_APPLICATION_MAIN_CLASS dk.sdu.datascience.Main
COPY ./postgresql-42.2.9.jar ./postgresql-42.2.9.jar
ENV SPARK_SUBMIT_ARGS="--driver-class-path postgresql-42.2.9.jar --jars postgresql-42.2.9.jar --verbose --driver-java-options \"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005\""