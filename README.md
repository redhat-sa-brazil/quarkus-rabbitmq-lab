# Quarkus RabbitMQ Lab

## Description

The following is intended to showcase how to integrate [Quarkus](https://quarkus.io/) and [RabbitMQ](https://www.rabbitmq.com/).

## Environment

- [OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Doocker](https://www.docker.com/)

## Building from Source

Source code artifacts are available in *source* and *docker* directories.

## Lab Script

0. [Start RabbitMQ](#demo-step-start-rabbitmq)
1. [Start rabbitmq-quarkus-lab app](#demo-step-start-rabbitmq)

### 0. Start RabbitMQ <a name="demo-step-start-rabbitmq"/>

* The easiest way to bootstrap **RabbitMQ** is using our custom *docker image*:  ` docker run -it -p 15672:15672 -p 5672:5672 viniciusmartinez/rabbitmq-quarkus:1.0`

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
  * a similar *output* is expected;
  * pleace notice that we added the [rabbitmq_delayed_message_exchange plugin](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)

* You can access the admin console via [http://localhost:15672](http://localhost:15672) with *guest* for both *Username* and *Password*
