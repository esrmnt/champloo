### Kafka Consumer

#### Receive Messages 
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic customer_orders --from-beginning

using a formatter

kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic customer_orders --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --from-beginning

##### Notes
- When you start a kafka-console-consumer, unless specifying the --from-beginning option, only future messages will be displayed and read.
- If the topic does not exist, the console consumer will automatically create it with default
- You can consume multiple topics at a time with a comma-delimited list or a pattern.
- If a consumer group id is not specified, the kafka-console-consumer generates a random consumer group.
- If messages do not appear in order, remember that the order is at the partition level, not at the topic level.


### Kafka Consumer Group

kafka-console-consumer --bootstrap-server localhost:9092 --topic customer_orders --group customer_orders_inv_mgmt 
kafka-console-consumer --bootstrap-server localhost:9092 --topic customer_orders --group customer_orders_inv_mgmt
kafka-console-consumer --bootstrap-server localhost:9092 --topic customer_orders --group customer_orders_inv_mgmt 
kafka-console-consumer --bootstrap-server localhost:9092 --topic customer_orders --group customer_orders_inv_mgmt 

Upon restart of a consumer in the group, the consumer will read from the latest committed offsets and read only the messages you've just produced

kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group customer_orders_inv_mgmt

##### Change the offsets
All the consumers must be stopped for the below changes

kafka-consumer-groups --bootstrap-server localhost:9092 --group customer_orders_inv_mgmt --reset-offsets --to-earliest --execute --topic customer_orders
kafka-consumer-groups --bootstrap-server localhost:9092 --group customer_orders_inv_mgmt --reset-offsets --shift-by -2 --execute --topic customer_orders