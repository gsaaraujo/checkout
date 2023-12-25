package com.gsa.ecommerce.payment.application.gateways;

import java.util.UUID;
import java.math.BigDecimal;
import java.util.Optional;

public interface OrderItemGateway {
  public record OrderItemDTO(UUID id, BigDecimal unitPrice, int quantity) {
  }

  Optional<OrderItemDTO> findOneById(UUID id);
}
