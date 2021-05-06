package br.com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class QuarkusRabbitMQMessagePriorityConsumer {

    @Inject
    RabbitMQClient rabbitMQClient;

    public void consume(){
        try {
            Connection connection = rabbitMQClient.connect();
            Channel channel = connection.createChannel();
            String messageA = new String( channel.basicGet( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_QUEUE_NAME, true).getBody() );
            System.out.println("Higher Priority: " + messageA);

            String messageB = new String( channel.basicGet( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_QUEUE_NAME, true).getBody() );
            System.out.println("Medium Priority: " + messageB);

            String messageC = new String( channel.basicGet( QuarkusRabbitMQConstants.MESSAGE_PRIORITY_QUEUE_NAME, true).getBody() );
            System.out.println("Lower Priority: " + messageC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
