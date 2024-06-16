import json
import logging as log

from kafka import KafkaProducer
from kafka.errors import KafkaError

# Create a producer with JSON serializer
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

# Sending JSON data
future = producer.send(topic ='orders', value = {'order-description': 'This is the order 007'}, partition = 1)
# Ensure all messages are sent before exiting
producer.flush()

# Block for 'synchronous' sends
try:
    record_metadata = future.get(timeout=10)
except KafkaError:
    # Decide what to do if produce request failed...
    log.exception()
    pass
# Successful result returns assigned partition and offset
print (record_metadata.topic)
print (record_metadata.partition)
print (record_metadata.offset)