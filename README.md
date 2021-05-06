# Quarkus RabbitMQ Lab

## Description

The following is intended to showcase how to integrate [Quarkus](https://quarkus.io/) and [RabbitMQ](https://www.rabbitmq.com/).

## Environment

- [OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Docker](https://www.docker.com/)
- [HTTPie](https://httpie.io/)

## Building from Source

Source code artifacts are available in *source* and *docker* directories.

## Lab Script

0. [Start RabbitMQ](#demo-step-start-rabbitmq)
1. [Start RabbitMQ Quarkus App](#demo-step-start-rabbitmq-quarkus-app)
2. [Send message to Default Exchange](#demo-step-send-message-default)
3. [Send Delayed message](#demo-step-send-delayed-message)

### 0. Start RabbitMQ <a name="demo-step-start-rabbitmq"/>

* The easiest way to bootstrap **RabbitMQ** is using our custom *docker image*:   

  `docker run -it -p 15672:15672 -p 5672:5672 viniciusmartinez/rabbitmq-quarkus:1.0`

* A similar output is expected:

  ```
  2021-05-06 17:03:31.703 [info] <0.702.0> Statistics database started.
  2021-05-06 17:03:31.704 [info] <0.701.0> Starting worker pool 'management_worker_pool' with 3 processes in it
  2021-05-06 17:03:31.969 [info] <0.9.0> Server startup complete; 4 plugins started.
   * rabbitmq_management
   * rabbitmq_management_agent
   * rabbitmq_web_dispatch
   * rabbitmq_delayed_message_exchange
   completed with 4 plugins.
  ```
  * pleace notice that we added the [rabbitmq_delayed_message_exchange plugin](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)

* You can access the admin console via [http://localhost:15672](http://localhost:15672) with *guest* for both *Username* and *Password*

### 1. Start RabbitMQ Quarkus App <a name="demo-step-start-rabbitmq-quarkus-app"/>

* Navigate to **source/rabbitmq-quarkus-lab** and bootstrap RabbitMQ Quarkus App: `mvn quarkus:dev`

* A similar output is expected:

  ```
  2021-05-06 14:16:48,627 INFO  [io.quarkus] (Quarkus Main Thread) rabbitmq-quarkus-lab 1.0.0-SNAPSHOT on JVM (powered by Quarkus 1.13.3.Final) started in 2.038s. Listening on: http://localhost:8080
  2021-05-06 14:16:48,627 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
  2021-05-06 14:16:48,628 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [cdi, rabbitmq-client, resteasy, resteasy-jsonb, smallrye-context-propagation]
  ```

  * if the following exception comes up, please restart the application and the problem will be gone;

    ```
    ... 47 more
    Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no queue 'quarkus.queue.default' in vhost '/', class-id=60, method-id=20)
    ```

### 2. Send message to Default Exchange <a name="demo-step-send-message-default"/>

* We´ve created some **Rest Endpoints** to act as **producers**. Therefore, just *POST* a message on **http://localhost:8080/rabbit**. Example:

  ```
  http POST :8080/rabbit message=message1
  HTTP/1.1 200 OK
  Content-Length: 23
  Content-Type: application/json

  {
     "message": "message1"
  }
  ```

* If you go back to **RabbitMQ Quarkus App** console, the following message is expected showcasing a working *consumer*:

  ```
  Sending message to Default: {"message": "message1"}
  Received message from
  Exchange
  Message: {"message": "message1"}
  ```

  * notice that we don´t have any *Exchange* information since it´s the default one;

### 3. Send Delayed message <a name="demo-step-send-delayed-message"/>

* Just *POST* a message on **http://localhost:8080/rabbit/delay**. Example:

  ```
  http POST :8080/rabbit/delay message=delayedmessage
  HTTP/1.1 200 OK
  Content-Length: 29
  Content-Type: application/json

  {
    "message": "delayedmessage"
  }
  ```

* If you go back to **RabbitMQ Quarkus App** console, the following message is expected showcasing a working *consumer*:

  ```
  Sending message to Delayed: {"message": "delayedmessage"}
  Received message from
  Exchange quarkus.exchange.delay
  Message: {"message": "delayedmessage"}
  ```

  * notice that consumption takes much longer (10000 ms);
