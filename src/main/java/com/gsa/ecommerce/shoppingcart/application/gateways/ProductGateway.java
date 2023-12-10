package com.gsa.ecommerce.shoppingcart.application.gateways;

import java.util.UUID;
import java.util.Optional;
import java.math.BigDecimal;

public interface ProductGateway {
  public record ProductDTO(UUID id, BigDecimal price) {
  }

  public Optional<ProductDTO> findOneById(UUID productId);
}
