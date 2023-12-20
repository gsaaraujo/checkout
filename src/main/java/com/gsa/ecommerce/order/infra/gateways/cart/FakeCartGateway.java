package com.gsa.ecommerce.order.infra.gateways.cart;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import com.gsa.ecommerce.order.application.gateways.CartGateway;

public class FakeCartGateway implements CartGateway {
  public ArrayList<CartDTO> carts;

  public FakeCartGateway() {
    this.carts = new ArrayList<CartDTO>();
  }

  @Override
  public Optional<CartDTO> findOneById(UUID id) {
    return this.carts.stream().filter(cart -> cart.id().equals(id)).findFirst();
  }

}
