package com.gsa.ecommerce.core.messagequeue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

@Component
public class RabbitMQMessageQueue implements MessageQueue {

  @Override
  public void publish(String queueName, Object message) {
    ConnectionFactory connectionFactory = new CachingConnectionFactory();
    AmqpAdmin admin = new RabbitAdmin(connectionFactory);
    Queue queue = new Queue(queueName);
    admin.declareQueue(queue);

    AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
    amqpTemplate.convertAndSend(queueName, message);
  }

}
