package com.gsa.ecommerce.core.infra.messagequeue;

public interface MessageQueue {
  public void publish(String queueName, Object message);
}