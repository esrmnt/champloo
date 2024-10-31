### Kafka Topics

#### Create 
kafka-topics --bootstrap-server localhost:9092 --create --topic customer_orders_raw --partitions 3 --replication-factor 1

##### Notes
- You cannot specify a replication factor greater than the number of brokers you have
- You can specify as many partitions as you want, 3 is a good number to get started with in dev
- You can set topic-level configurations https://kafka.apache.org/documentation/#topicconfigs using the --config

#### List

kafka-topics --bootstrap-server localhost:9092 --list

##### Notes
- This command lists internal topics that you won't have created (such as __consumer_offsets). 
- Do not try to delete these topics. 
- Use --exclude-internal if you want to hide these topics

#### Describe 
kafka-topics --bootstrap-server localhost:9092 --describe --topic customer_orders_raw

#### Alter
kafka-topics --bootstrap-server localhost:9092 --alter --topic customer_orders_raw --partitions 8

##### Notes

- This command is NOT RECOMMENDED when consumers are relying on key-based ordering as changing the number of partitions changes the key-hashing technique

- You can only add partitions, not remove partitions

#### Delete
kafka-topics --bootstrap-server localhost:9092 --delete --topic customer_orders_raw

##### Notes
- In case topic deletion is not enabled (delete.topic.enable broker setting) then the topics will be "marked for deletion" but will not be deleted
- Deleting a topic in Kafka may take some time and this is why the kafka-topics command returns an empty output even before the topic is deleted (only the command is sent)
