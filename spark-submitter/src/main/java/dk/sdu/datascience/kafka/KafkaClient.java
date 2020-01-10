
package dk.sdu.datascience.kafka;

import dk.sdu.datascience.kafka.structure.messages.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
public class KafkaClient{
    /**
     * Publishes when new data is being processed
     * @param statusMessage to send as payload
     */
	public static void producedatascienceProcessedStatus(StatusMessage statusMessage) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
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
		} catch (ExecutionException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
			producer.flush();
			producer.close();
		}
	}
	public interface energidataElspotCallback { 
		// this can be any type of method 
		void messageConsumed(EnerginetElspot payload); 
	} 

    /**
    * subscribes to event for when new data is being processed
    * @param callback which should be called when data is consumed
    */
    public static void consumerEnerginetElspot(energidataElspotCallback callback) {
        Thread thread = new Thread(() -> {
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataCo2Emission"));
            while(true){
                consumerEnerginetCO2Emission(consumer, callback);
            }
        });
        thread.start();
    }

    private static void consumerEnerginetElspot(Consumer<Long, String> consumer,energidataElspotCallback callback) {
            consumer.subscribe(Collections.singletonList("energidataElspot"));
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
                EnerginetElspot data = gson.fromJson("" + consumerRecord.value(), EnerginetElspot.class);
                callback.messageConsumed(data);
            }
            // commits the offset of record to broker.
            consumer.commitAsync();
        }
	public interface energidataCo2EmissionCallback { 
		// this can be any type of method 
		void messageConsumed(EnerginetCO2Emission payload); 
	} 

    /**
    * subscribes to event for when new data is being processed
    * @param callback which should be called when data is consumed
    */
    public static void consumerEnerginetCO2Emission(energidataCo2EmissionCallback callback) {
        Thread thread = new Thread(() -> {
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataCo2Emission"));
            while(true){
                consumerEnerginetCO2Emission(consumer, callback);
            }
        });
        thread.start();
    }

    private static void consumerEnerginetCO2Emission(Consumer<Long, String> consumer,energidataCo2EmissionCallback callback) {
            consumer.subscribe(Collections.singletonList("energidataCo2Emission"));
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
                EnerginetCO2Emission data = gson.fromJson("" + consumerRecord.value(), EnerginetCO2Emission.class);
                callback.messageConsumed(data);
            }
            // commits the offset of record to broker.
            consumer.commitAsync();
        }
	public interface energidataProductionAndExchangeCallback { 
		// this can be any type of method 
		void messageConsumed(EnerginetProductionAndExchange payload); 
	} 

    /**
    * subscribes to event for when new data is being processed
    * @param callback which should be called when data is consumed
    */
    public static void consumerEnerginetProductionAndExchange(energidataProductionAndExchangeCallback callback) {
        Thread thread = new Thread(() -> {
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataCo2Emission"));
            while(true){
                consumerEnerginetCO2Emission(consumer, callback);
            }
        });
        thread.start();
    }

    private static void consumerEnerginetProductionAndExchange(Consumer<Long, String> consumer,energidataProductionAndExchangeCallback callback) {
            consumer.subscribe(Collections.singletonList("energidataProductionAndExchange"));
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
                EnerginetProductionAndExchange data = gson.fromJson("" + consumerRecord.value(), EnerginetProductionAndExchange.class);
                callback.messageConsumed(data);
            }
            // commits the offset of record to broker.
            consumer.commitAsync();
        }
    /**
     * subscribes to event for when new data is being processed
     * @param processedProduced to send as payload
     */
	public static void produceprocessedProduced(ProcessedProduced processedProduced) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedProduced);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedProduced",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " +
							"meta(partition=%d, offset=%d) time=%d\n",
					record.key(), record.value(), metadata.partition(),
					metadata.offset(), elapsedTime);
		} catch (ExecutionException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
			producer.flush();
			producer.close();
		}
	}
    /**
     * subscribes to event for when new data is being processed
     * @param processedSpotPrices to send as payload
     */
	public static void produceprocessedSpotPrices(ProcessedSpotPrices processedSpotPrices) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedSpotPrices);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedSpotPrices",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " +
							"meta(partition=%d, offset=%d) time=%d\n",
					record.key(), record.value(), metadata.partition(),
					metadata.offset(), elapsedTime);
		} catch (ExecutionException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
			producer.flush();
			producer.close();
		}
	}
    /**
     * subscribes to event for when new data is being processed
     * @param processedEmissions to send as payload
     */
	public static void produceprocessedEmissions(ProcessedEmissions processedEmissions) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedEmissions);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedEmissions",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " +
							"meta(partition=%d, offset=%d) time=%d\n",
					record.key(), record.value(), metadata.partition(),
					metadata.offset(), elapsedTime);
		} catch (ExecutionException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(KafkaClient.class.getName()).log(Level.SEVERE, null, ex);
        }  finally {
			producer.flush();
			producer.close();
		}
	}
}


