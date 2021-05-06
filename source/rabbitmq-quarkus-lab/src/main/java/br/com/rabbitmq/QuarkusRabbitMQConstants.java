package br.com.rabbitmq;

public interface QuarkusRabbitMQConstants {

    public final String DEFAULT_EXCHANGE_NAME = "";
    public final String DEFAULT_QUEUE_NAME = "quarkus.queue.default";

    public final String DELAY_EXCHANGE_NAME = "quarkus.exchange.delay";
    public final String DELAY_QUEUE_NAME = "quarkus.queue.delay";
    public final String DELAY_HEADER = "x-delayed-type";
    public final String DELAY_HEADER_VALUE = "direct";
    public final String DELAY_HEADER_PRODUCER = "x-delay";
    public final String DELAY_EXCHANGE_HEADER = "x-delayed-message";
    public final String DELAY_ROUTING_KEY = "#";

    public final String MESSAGE_PRIORITY_EXCHANGE_NAME = "quarkus.exchange.message.priority";
    public final String MESSAGE_PRIORITY_QUEUE_NAME = "quarkus.queue.message.priority";
    public final String MESSAGE_PRIORITY_HEADER = "x-max-priority";
    public final Integer MESSAGE_PRIORITY_HEADER_VALUE = 10;
    public final String MESSAGE_PRIORITY_MESSAGE_1 = "MESSAGE 1";
    public final Integer MESSAGE_PRIORITY_MESSAGE_1_PRIORITY = 1;
    public final String MESSAGE_PRIORITY_MESSAGE_2 = "MESSAGE 2";
    public final Integer MESSAGE_PRIORITY_MESSAGE_2_PRIORITY = 2;
    public final String MESSAGE_PRIORITY_MESSAGE_3 = "MESSAGE 3";
    public final Integer MESSAGE_PRIORITY_MESSAGE_3_PRIORITY = 3;
    public final String MESSAGE_PRIORITY_ROUTING_KEY = "#";

    public final String DEAD_LETTER_EXCHANGE_NAME = "quarkus.exchange.dead.letter";
    public final String DEAD_LETTER_QUEUE_NAME = "quarkus.queue.dead.letter";
    public final String DEAD_LETTER_EXCHANGE_HEADER = "x-dead-letter-exchange";
    public final String DEAD_LETTER_ROUTING_HEADER = "x-dead-letter-routing-key";
    public final String DEAD_LETTER_ROUTING_VALUE = "deadLetter";

    public final String RETRY_EXCHANGE_NAME = "quarkus.exchange.retry";
    public final String RETRY_QUEUE_NAME = "quarkus.queue.retry";
    public final String RETRY_ROUTING_KEY = "#";



}
