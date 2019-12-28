http://wurstmeister.github.io/kafka-docker/ <- REFERENCE ->

# Kafka Shell

You can interact with your Kafka cluster via the Kafka shell:

`$ start-kafka-shell.sh <DOCKER_HOST_IP> <ZK_HOST:ZK_PORT>`

## Testing

To test your setup, start a shell, create a topic and start a producer:

```
$ $KAFKA_HOME/bin/kafka-topics.sh --create --topic messageA --partitions 4 --zookeeper 192.168.0.11:2181 --replication-factor 2
$ $KAFKA_HOME/bin/kafka-topics.sh --describe --topic topic --zookeeper $ZK
$ $KAFKA_HOME/bin/kafka-console-producer.sh --topic=topic \
--broker-list=`broker-list.sh`
```

Start another shell and start a consumer:

`$ $KAFKA_HOME/bin/kafka-console-consumer.sh --topic=topic --zookeeper=\$ZK`
