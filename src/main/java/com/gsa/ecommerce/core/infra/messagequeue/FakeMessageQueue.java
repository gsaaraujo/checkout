package com.gsa.ecommerce.core.infra.messagequeue;

import java.util.Map;
import java.util.HashMap;

public class FakeMessageQueue implements MessageQueue {
  public Map<String, Object> messages;

  public FakeMessageQueue() {
    this.messages = new HashMap<String, Object>();
  }

  @Override
  public void publish(String queueName, String message) {
    this.messages.put(queueName, message);
  }
}
