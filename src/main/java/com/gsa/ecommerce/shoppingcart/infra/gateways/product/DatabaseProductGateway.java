package com.gsa.ecommerce.shoppingcart.infra.gateways.product;

import java.util.UUID;
import java.util.Optional;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.ecommerce.core.database.ProductDatabase;
import com.gsa.ecommerce.core.database.orms.ProductOrm;
import com.gsa.ecommerce.shoppingcart.application.gateways.ProductGateway;

@Component
public class DatabaseProductGateway implements ProductGateway {
  private final ProductDatabase productDatabase;

  public DatabaseProductGateway(ProductDatabase productDatabase) {
    this.productDatabase = productDatabase;
  }

  @Override
  public Optional<ProductDTO> findOneById(UUID productId) {
    Optional<ProductOrm> productFound = this.productDatabase.findById(productId.toString());

    if (productFound.isEmpty()) {
      return Optional.empty();
    }

    ProductDTO productDTO = new ProductDTO(UUID.fromString(productFound.get().id()),
        new BigDecimal(productFound.get().price()));

    return Optional.of(productDTO);
  }

}
