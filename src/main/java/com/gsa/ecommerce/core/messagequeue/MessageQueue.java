package com.gsa.ecommerce.core.messagequeue;

public interface MessageQueue {
  public void publish(String queueName, Object message);
}