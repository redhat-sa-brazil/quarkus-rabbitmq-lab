package br.com.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class RabbitMQSetup {

    @Inject
    private RabbitMQClient rabbitMQClient;

    public void onApplicationStart(@Observes StartupEvent event) {
        setupQueues();
    }

    private void setupQueues() {
        try {
            Connection connection = rabbitMQClient.connect();
            Channel channel = connection.createChannel();

            // Default
            channel.queueDeclare( QuarkusRabbitMQConstants.DEFAULT_QUEUE_NAME , false, false, false, null );

            // Delayed
            Map<String, Object> delayArgs = new HashMap<String, Object>();
            delayArgs.put( QuarkusRabbitMQConstants.DELAY_HEADER, QuarkusRabbitMQConstants.DELAY_HEADER_VALUE );
            channel.exchangeDeclare( QuarkusRabbitMQConstants.DELAY_EXCHANGE_NAME, QuarkusRabbitMQConstants.DELAY_EXCHANGE_HEADER , true, false, delayArgs );
            channel.queueDeclare( QuarkusRabbitMQConstants.DELAY_QUEUE_NAME , true, false , false, null );
            channel.queueBind( QuarkusRabbitMQConstants.DELAY_QUEUE_NAME,
                                QuarkusRabbitMQConstants.DELAY_EXCHANGE_NAME,
                                QuarkusRabbitMQConstants.DELAY_ROUTING_KEY );

            // Message Priority
            channel.exchangeDeclare( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true  );
            Map<String, Object> messagePriorityArgs = new HashMap<>();
            messagePriorityArgs.put( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_HEADER, QuarkusRabbitMQConstants.MESSAGE_PRIORITY_HEADER_VALUE );
            channel.queueDeclare( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_QUEUE_NAME , true, false , false, messagePriorityArgs );
            channel.queueBind( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_QUEUE_NAME,
                                QuarkusRabbitMQConstants.MESSAGE_PRIORITY_EXCHANGE_NAME,
                                QuarkusRabbitMQConstants.MESSAGE_PRIORITY_ROUTING_KEY );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupConsumer(String queueName, Consumer consumer){
        try {
            Connection connection = rabbitMQClient.connect();
            Channel channel = connection.createChannel();
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
