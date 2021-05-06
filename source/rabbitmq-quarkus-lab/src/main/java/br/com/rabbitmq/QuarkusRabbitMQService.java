package br.com.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.impl.AMQBasicProperties;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class QuarkusRabbitMQService {

    @Inject
    private RabbitMQClient rabbitMQClient;

    private Channel channel;

    @Inject
    private QuarkusRabbitMQMessagePriorityConsumer quarkusRabbitMQMessagePriorityConsumer;

    public void send(String message) {
        try {
            Connection connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            System.out.println( "Sending message to Default: " + message );
            channel.basicPublish( QuarkusRabbitMQConstants.DEFAULT_EXCHANGE_NAME,
                                    QuarkusRabbitMQConstants.DEFAULT_QUEUE_NAME,
                                    null,
                                    message.getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDelayedMessage(String message, Long delay) {
        try {
            Connection connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            System.out.println( "Sending message to Delayed: " + message );
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put(QuarkusRabbitMQConstants.DELAY_HEADER_PRODUCER, delay);
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
            channel.basicPublish( QuarkusRabbitMQConstants.DELAY_EXCHANGE_NAME,
                                    QuarkusRabbitMQConstants.DELAY_ROUTING_KEY,
                                    props.build(),
                                    message.getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPriorityMessage() {
        try {
            Connection connection = rabbitMQClient.connect();
            channel = connection.createChannel();
            System.out.println("Sending Priority Messages");

            //sending first message with lower priority
            channel.basicPublish(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_EXCHANGE_NAME,
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_ROUTING_KEY,
                    new AMQP.BasicProperties().builder().priority(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_1_PRIORITY).build(),
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_1.getBytes());
            System.out.println("Message Sent: " + QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_1);

            //sending second message
            channel.basicPublish(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_EXCHANGE_NAME,
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_ROUTING_KEY,
                    new AMQP.BasicProperties().builder().priority(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_2_PRIORITY).build(),
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_2.getBytes());
            System.out.println("Message Sent: " + QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_2);

            //sending third message with higher priority
            channel.basicPublish(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_EXCHANGE_NAME,
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_ROUTING_KEY,
                    new AMQP.BasicProperties().builder().priority(QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_3_PRIORITY).build(),
                    QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_3.getBytes());
            System.out.println("Message Sent: " + QuarkusRabbitMQConstants.MESSAGE_PRIORITY_MESSAGE_3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void consumePriorityMessage() {
            quarkusRabbitMQMessagePriorityConsumer.consume();
        }
}

