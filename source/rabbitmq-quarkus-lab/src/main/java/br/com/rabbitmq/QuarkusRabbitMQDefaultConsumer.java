package br.com.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Dependent
public class QuarkusRabbitMQDefaultConsumer implements Consumer {

    public void onApplicationStart(@Observes StartupEvent event, RabbitMQSetup rabbitMQSetup) {
        rabbitMQSetup.setupConsumer( QuarkusRabbitMQConstants.DEFAULT_QUEUE_NAME, this ) ;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        String message = new String(body, StandardCharsets.UTF_8);
        System.out.println( "Received message from" );
        System.out.println( "Exchange " + envelope.getExchange() );
        System.out.println( "Message: " + message);
    }

    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

}
