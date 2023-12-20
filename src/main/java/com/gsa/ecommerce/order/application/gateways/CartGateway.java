package com.gsa.ecommerce.order.application.gateways;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public interface CartGateway {
  public record CartDTO(UUID id, List<CartItemDTO> items) {
  }

  public record CartItemDTO(UUID id, UUID productId, BigDecimal price, int quantity) {
  }

  public Optional<CartDTO> findOneById(UUID id);
}
