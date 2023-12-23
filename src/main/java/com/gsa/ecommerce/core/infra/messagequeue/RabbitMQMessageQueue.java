package com.gsa.ecommerce.core.infra.messagequeue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.AmqpTemplate;

@Component
public class RabbitMQMessageQueue implements MessageQueue {
  private final AmqpAdmin admin;
  private final AmqpTemplate amqpTemplate;

  public RabbitMQMessageQueue(AmqpAdmin admin, AmqpTemplate amqpTemplate) {
    this.admin = admin;
    this.amqpTemplate = amqpTemplate;
  }

  @Override
  public void publish(String queueName, String message) {
    Queue queue = new Queue(queueName);
    this.admin.declareQueue(queue);
    amqpTemplate.convertAndSend(queueName, message);
  }
}
