### Kafka Producer

#### Send Messages 
kafka-console-producer --bootstrap-server localhost:9092 --topic customer_orders

##### Notes
- Messages are sent with the null key by default (see below for more options)
- If the topic does not exist, it can be auto-created by Kafka:
A topic with the name provided should exist. If you specify a topic that does not exist yet, a new topic with the name provided will be created with the default number of partitions and replication factor.
These are controlled by the broker-side settings (in your config/server.properties file), with the following defaults:
```
auto.create.topics.enable=true
num.partitions=1
default.replication.factor=1
```
- To enable message compression, default gzip, possible values 'none', 'gzip', 'snappy', 'lz4', or 'zstd', using --compression-codec

- To pass in any producer property, such as the acks=all setting, use --producer-property

- An alternative to set the acks setting directly, use --request-required-acks

#### Produce messages with key

kafka-console-producer --bootstrap-server localhost:9092 --topic customer_orders --property parse.key=true --property key.separator=:

#### About acks 

Kafka producer configuration parameter called acks is the number of brokers who need to acknowledge receiving the message before it is considered a successful write.

##### acks=0
When acks=0 producers consider messages as "written successfully" the moment the message was sent without waiting for the broker to accept it at all. If the broker goes offline or an exception happens, we won’t know and will lose data. This is useful for data where it’s okay to potentially lose messages, such as metrics collection, and produces the highest throughput setting because the network overhead is minimized.

##### acks = 1
When acks=1 , producers consider messages as "written successfully" when the message was acknowledged by only the leader. Leader response is requested, but replication is not a guarantee as it happens in the background. If an ack is not received, the producer may retry the request. If the leader broker goes offline unexpectedly but replicas haven’t replicated the data yet, we have a data loss.

##### acks = all
When acks=all, producers consider messages as "written successfully" when the message is accepted by all in-sync replicas (ISR). The lead replica for a partition checks to see if there are enough in-sync replicas for safely writing the message (controlled by the broker setting min.insync.replicas). The request will be stored in a buffer until the leader observes that the follower replicas replicated the message, at which point a successful acknowledgement is sent back to the client.

### references 
- https://www.conduktor.io/kafka/kafka-producer-acks-deep-dive/