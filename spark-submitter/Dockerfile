FROM bde2020/spark-java-template:2.4.0-hadoop2.7


ENV SPARK_APPLICATION_JAR_NAME spark-submitter-1.0.0-with-dependencies
ENV SPARK_APPLICATION_MAIN_CLASS dk.sdu.datascience.Main
ENV SPARK_SUBMIT_ARGS="--driver-java-options -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"