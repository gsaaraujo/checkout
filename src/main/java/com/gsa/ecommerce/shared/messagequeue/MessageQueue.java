package com.gsa.ecommerce.shared.messagequeue;

public interface MessageQueue {
  public void publish(String queueName, Object message);
}