package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.util.Optional;

public interface CartRepository {
  public Cart create(Cart cart);

  public Optional<Cart> findOneById(UUID id);

  public Cart update(Cart cart);

}
