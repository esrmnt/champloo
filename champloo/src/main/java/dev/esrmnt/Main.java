package dev.esrmnt;

import org.slf4j.Logger;
import java.util.Properties;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("here comes the producer");

        String bootstrapServers = "127.0.0.1:9092";
        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 31; i < 61; i++) {
            // create a producer record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("customer_orders_raw",
                    "order number " + String.format("%02d", i));

            // send data - asynchronous
            producer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        logger.info(String.format("Received new metadata, Topic %s Partition %s Offset %s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()));
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            });
        }

        // flush data - synchronous
        producer.flush();

        // flush and close producer
        producer.close();
    }
}