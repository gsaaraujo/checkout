package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.util.Optional;

public interface CartRepository {
  public void create(Cart cart);

  public Optional<Cart> findOneByCustomerId(UUID customerId);

  public void update(Cart cart);

}
