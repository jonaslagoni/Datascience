
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
import java.time.Duration;
import java.util.Iterator;
public class KafkaClient{
    /**
     * Publishes when new data is being processed
     * @param statusMessage to send as payload
     */
	public static void producedatascienceProcessedStatus(StatusMessage statusMessage) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
        // Create a Logger 
        Logger logger = Logger.getLogger("producedatascienceProcessedStatus"); 
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(statusMessage);
        logger.log(Level.INFO, "Sending record with values: {0}", payload);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("datascienceProcessedStatus",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
            logger.log(Level.INFO, "Sent record with value: {0}, with elapsed time: {1}", new Object[] { record.value() , elapsedTime });
		} catch (ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
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
            Logger logger = Logger.getLogger("consumerEnerginetElspot | Thread"); 
            logger.log(Level.INFO, "Start consuming on subject");
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataElspot"));
            while(true){
                logger.log(Level.INFO, "Consuming data");
                consumerEnerginetElspot(consumer, callback);
                logger.log(Level.INFO, "Consumed data, waiting 5 sec to continue.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        });
        thread.start();
    }

    private static void consumerEnerginetElspot(Consumer<Long, String> consumer,energidataElspotCallback callback) {
        Logger logger = Logger.getLogger("consumerEnerginetElspot"); 
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
        // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
        if (consumerRecords.count() == 0) {
            logger.log(Level.FINE, "No records found");
            return;
        }
        Gson gson = new Gson();
        //print each record.
        Iterator<ConsumerRecord<Long, String>> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            ConsumerRecord consumerRecord = iterator.next();
            logger.log(Level.INFO, "Record consumed, with key: {0}, value: {1}, partition: {2}, offset: {3}", new Object[] { consumerRecord.key(), consumerRecord.value(), consumerRecord.partition(), consumerRecord.offset()});
            EnerginetElspot data = gson.fromJson("" + consumerRecord.value(), EnerginetElspot.class);
            callback.messageConsumed(data);
        }
        // commits the offset of record to broker.
        logger.log(Level.FINE, "Comitting offset");
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
            Logger logger = Logger.getLogger("consumerEnerginetCO2Emission | Thread"); 
            logger.log(Level.INFO, "Start consuming on subject");
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataCo2Emission"));
            while(true){
                logger.log(Level.INFO, "Consuming data");
                consumerEnerginetCO2Emission(consumer, callback);
                logger.log(Level.INFO, "Consumed data, waiting 5 sec to continue.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        });
        thread.start();
    }

    private static void consumerEnerginetCO2Emission(Consumer<Long, String> consumer,energidataCo2EmissionCallback callback) {
        Logger logger = Logger.getLogger("consumerEnerginetCO2Emission"); 
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
        // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
        if (consumerRecords.count() == 0) {
            logger.log(Level.FINE, "No records found");
            return;
        }
        Gson gson = new Gson();
        //print each record.
        Iterator<ConsumerRecord<Long, String>> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            ConsumerRecord consumerRecord = iterator.next();
            logger.log(Level.INFO, "Record consumed, with key: {0}, value: {1}, partition: {2}, offset: {3}", new Object[] { consumerRecord.key(), consumerRecord.value(), consumerRecord.partition(), consumerRecord.offset()});
            EnerginetCO2Emission data = gson.fromJson("" + consumerRecord.value(), EnerginetCO2Emission.class);
            callback.messageConsumed(data);
        }
        // commits the offset of record to broker.
        logger.log(Level.FINE, "Comitting offset");
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
            Logger logger = Logger.getLogger("consumerEnerginetProductionAndExchange | Thread"); 
            logger.log(Level.INFO, "Start consuming on subject");
            Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
            consumer.subscribe(Collections.singletonList("energidataProductionAndExchange"));
            while(true){
                logger.log(Level.INFO, "Consuming data");
                consumerEnerginetProductionAndExchange(consumer, callback);
                logger.log(Level.INFO, "Consumed data, waiting 5 sec to continue.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        });
        thread.start();
    }

    private static void consumerEnerginetProductionAndExchange(Consumer<Long, String> consumer,energidataProductionAndExchangeCallback callback) {
        Logger logger = Logger.getLogger("consumerEnerginetProductionAndExchange"); 
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
        // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
        if (consumerRecords.count() == 0) {
            logger.log(Level.FINE, "No records found");
            return;
        }
        Gson gson = new Gson();
        //print each record.
        Iterator<ConsumerRecord<Long, String>> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            ConsumerRecord consumerRecord = iterator.next();
            logger.log(Level.INFO, "Record consumed, with key: {0}, value: {1}, partition: {2}, offset: {3}", new Object[] { consumerRecord.key(), consumerRecord.value(), consumerRecord.partition(), consumerRecord.offset()});
            EnerginetProductionAndExchange data = gson.fromJson("" + consumerRecord.value(), EnerginetProductionAndExchange.class);
            callback.messageConsumed(data);
        }
        // commits the offset of record to broker.
        logger.log(Level.FINE, "Comitting offset");
        consumer.commitAsync();
    }
    /**
     * subscribes to event for when new data is being processed
     * @param processedProduced to send as payload
     */
	public static void produceprocessedProduced(ProcessedProduced processedProduced) {
		final Producer<Long, String> producer = ProducerCreator.createProducer();
        // Create a Logger 
        Logger logger = Logger.getLogger("produceprocessedProduced"); 
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedProduced);
        logger.log(Level.INFO, "Sending record with values: {0}", payload);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedProduced",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
            logger.log(Level.INFO, "Sent record with value: {0}, with elapsed time: {1}", new Object[] { record.value() , elapsedTime });
		} catch (ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
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
        // Create a Logger 
        Logger logger = Logger.getLogger("produceprocessedSpotPrices"); 
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedSpotPrices);
        logger.log(Level.INFO, "Sending record with values: {0}", payload);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedSpotPrices",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
            logger.log(Level.INFO, "Sent record with value: {0}, with elapsed time: {1}", new Object[] { record.value() , elapsedTime });
		} catch (ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
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
        // Create a Logger 
        Logger logger = Logger.getLogger("produceprocessedEmissions"); 
		long time = System.currentTimeMillis();
		Gson gson = new Gson();
		String payload = gson.toJson(processedEmissions);
        logger.log(Level.INFO, "Sending record with values: {0}", payload);
		try {
			final ProducerRecord<Long, String> record =
					new ProducerRecord<>("processedEmissions",
								payload);

			RecordMetadata metadata = producer.send(record).get();

			long elapsedTime = System.currentTimeMillis() - time;
            logger.log(Level.INFO, "Sent record with value: {0}, with elapsed time: {1}", new Object[] { record.value() , elapsedTime });
		} catch (ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }  finally {
			producer.flush();
			producer.close();
		}
	}
}


