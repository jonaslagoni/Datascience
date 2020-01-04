
package dk.sdu.datascience.kafka;

import dk.sdu.datascience.kafka.structure.messages.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import com.google.gson.Gson;
import java.util.Collections;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
public class KafkaClient{
    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                            "127.0.0.1");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                        LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                    StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
	static void producedatascienceProcessedStatus(StatusMessage statusMessage) throws Exception {
		final Producer<Long, String> producer = createProducer();
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(statusMessage);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("datascienceProcessedStatus",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " +
							"meta(partition=%d, offset=%d) time=%d\n",
					record.key(), record.value(), metadata.partition(),
					metadata.offset(), elapsedTime);
		} finally {
			producer.flush();
			producer.close();
		}
	}
	interface datascienceWarehouseDataCallback { 
		// this can be any type of method 
		void messageConsumed(RawEnergyDataMessage callback); 
	} 
    static void consumerRawEnergyDataMessage(datascienceWarehouseDataCallback callback) {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        consumer.subscribe(Collections.singletonList("datascienceWarehouseData"));
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
        // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
        if (consumerRecords.count() == 0) {
            System.out.println("No records found");
            return;
        }
        Gson gson = new Gson();
        //print each record.
        ConsumerRecord consumerRecord = null;
        while (consumerRecords.iterator().hasNext()) {
            consumerRecord = consumerRecords.iterator().next();
            System.out.println("Record Key " + consumerRecord.key());
            System.out.println("Record value " + consumerRecord.value());
            System.out.println("Record partition " + consumerRecord.partition());
            System.out.println("Record offset " + consumerRecord.offset());
        }
        if (consumerRecord != null) {
            RawEnergyDataMessage data = gson.fromJson("" + consumerRecord.value(), RawEnergyDataMessage.class);
            callback.messageConsumed(data);
        }
        // commits the offset of record to broker.
        consumer.commitAsync();
        consumer.close();
    }
}