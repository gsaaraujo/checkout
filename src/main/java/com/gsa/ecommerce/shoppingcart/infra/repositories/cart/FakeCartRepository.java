package com.gsa.ecommerce.shoppingcart.infra.repositories.cart;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import com.gsa.ecommerce.shoppingcart.domain.models.cart.Cart;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartRepository;

public class FakeCartRepository implements CartRepository {
  public ArrayList<Cart> carts;

  public FakeCartRepository() {
    this.carts = new ArrayList<>();
  }

  @Override
  public void create(Cart cart) {
    this.carts.add(cart);
  }

  @Override
  public Optional<Cart> findOneByCustomerId(UUID customerId) {
    return this.carts.stream().filter(cart -> cart.customerId().equals(customerId)).findFirst();
  }

  @Override
  public void update(Cart updatedCart) {
    Optional<Cart> cartFound = this.carts.stream().filter(cart -> cart.id().equals(updatedCart.id())).findFirst();
    if (cartFound.isPresent()) {
      this.carts.remove(cartFound.get());
      this.carts.add(updatedCart);
    }
  }
}
