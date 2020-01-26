/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.datascience.kafka;

public interface IKafkaConstants {

    public static String KAFKA_BROKERS = "kafka:9092, kafka-2:9093, kafka-3:9094";
    public static Integer MESSAGE_COUNT = 1000;
    public static String CLIENT_ID = "spark-submitter-id";
    public static String GROUP_ID_CONFIG = "spark-submitter-group";
    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
    public static String OFFSET_RESET_LATEST = "latest";
    public static String OFFSET_RESET_EARLIER = "earliest";
    public static Integer MAX_POLL_RECORDS = 1;
}
