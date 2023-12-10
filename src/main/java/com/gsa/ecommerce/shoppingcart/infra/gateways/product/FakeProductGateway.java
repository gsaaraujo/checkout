package com.gsa.ecommerce.shoppingcart.infra.gateways.product;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import com.gsa.ecommerce.shoppingcart.application.gateways.ProductGateway;

public class FakeProductGateway implements ProductGateway {
  public ArrayList<ProductDTO> products;

  public FakeProductGateway() {
    this.products = new ArrayList<ProductDTO>();
  }

  @Override
  public Optional<ProductDTO> findOneById(UUID productId) {
    return this.products.stream().filter(product -> product.id().equals(productId)).findFirst();
  }
}
