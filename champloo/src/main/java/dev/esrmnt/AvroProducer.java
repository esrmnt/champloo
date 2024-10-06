package dev.esrmnt;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.examples.clients.basicavro.Payment;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class AvroProducer {

    private static Logger logger = LoggerFactory.getLogger(AvroProducer.class);

    public static void main(String[] args) {

        final String topic = "customer_payments";   
        final String bootstrapServers = "localhost:9092";
        final String schemaRegisteryUrl = "http://localhost:8081";
        
        Properties properties = SetProducerProperties(bootstrapServers, schemaRegisteryUrl);

        // create the producer
        KafkaProducer<String, Payment> producer = new KafkaProducer<String, Payment>(properties);

        for (int i = 61; i < 65; i++) {

            String orderId = String.format("OrderNumber %02d", i);
            Double amount  = Double.valueOf(i);

            Payment customerPayment = new Payment(orderId, amount);

            // create a producer record
            ProducerRecord<String, Payment> producerRecord = new ProducerRecord<String, Payment>(topic, customerPayment.getId().toString(), customerPayment);

            // send data - asynchronous
            producer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    // executes every time a record is successfully sent or an exception is thrown
                    if (e == null) {
                        // the record was successfully sent
                        logger.info(String.format("Received new metadata, Topic %s Partition %s Offset %s",
                                recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()));
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

    private static Properties SetProducerProperties(final String bootstrapServers, final String schemaRegisteryUrl) {
        // create Producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegisteryUrl);
        return properties;
    }
}