package com.gsa.ecommerce.order.application.gateways;

import java.util.UUID;

public interface CustomerGateway {
  public record CustomerDTO(UUID id) {
  }

  public boolean exists(UUID customerId);
}
