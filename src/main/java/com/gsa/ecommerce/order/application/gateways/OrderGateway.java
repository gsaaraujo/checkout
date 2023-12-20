package com.gsa.ecommerce.order.application.gateways;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

public interface OrderGateway {
  public record OrderDTO(UUID id, UUID customerId, List<OrderItemDTO> items) {
  }

  public record OrderItemDTO(UUID id, UUID productId, BigDecimal price, int quantity) {
  }

  public void create(OrderDTO orderDTO);
}
