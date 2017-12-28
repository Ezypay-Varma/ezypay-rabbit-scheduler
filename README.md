# ezypay-rabbit-scheduler
### Prerequisite 
Rabbitmq 

Java

mvn

### Overview 
Scheduler using rabbitmq is to set up dead lettering with time to live on the dead-letter queue (DLQ), as well as dead-letter configuration on the DLQ itself. See RabbitMQ Binder Properties for more information about the properties discussed here. Example configuration to enable this feature:

Set autoBindDlq to true - the binder will create a DLQ; you can optionally specify a name in deadLetterQueueName

Set dlqTtl to the back off time you want to wait between redeliveries

Set the dlqDeadLetterExchange to the default exchange - expired messages from the DLQ will be routed to the original queue since the default deadLetterRoutingKey is the queue name (destination.group)

To force a message to be dead-lettered, either throw an AmqpRejectAndDontRequeueException, or set requeueRejected to true and throw any exception.

The loop will continue without end, which is fine for transient problems but you may want to give up after some number of attempts. Fortunately, RabbitMQ provides the x-death header which allows you to determine how many cycles have occurred.

To acknowledge a message after giving up, throw an ImmediateAcknowledgeAmqpException

### Configuraion
```Java
spring.cloud.stream.bindings.input.destination=myDestnation
spring.cloud.stream.bindings.input.group=consumerGroup
spring.cloud.stream.bindings.input.consumer.max-attempts=1
spring.cloud.stream.rabbit.bindings.input.consumer.auto-bind-dlq=true
spring.cloud.stream.rabbit.bindings.input.consumer.dlq-ttl=5000
spring.cloud.stream.rabbit.bindings.input.consumer.dlq-dead-letter-exchange=
```

This configuration creates an exchange myDestination with queue myDestination.consumerGroup bound to a topic exchange with a wildcard routing key #. It creates a DLQ bound to a direct exchange DLX with routing key myDestination.consumerGroup. When messages are rejected, they are routed to the DLQ. After 5 seconds, the message expires and is routed to the original queue using the queue name as the routing key.
