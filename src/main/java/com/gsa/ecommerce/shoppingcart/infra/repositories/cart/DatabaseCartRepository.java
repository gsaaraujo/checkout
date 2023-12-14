package com.gsa.ecommerce.shoppingcart.infra.repositories.cart;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.ecommerce.shoppingcart.domain.models.cart.Cart;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartItem;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartRepository;
import com.gsa.ecommerce.shoppingcart.infra.database.CartItemDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.CartDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.CustomerDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.ProductDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.CartItemOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.CartOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.CustomerOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.ProductOrm;

@Service
public class DatabaseCartRepository implements CartRepository {
  private final CartDatabase cartDatabase;
  private final CartItemDatabase cartItemDatabase;
  private final CustomerDatabase customerDatabase;
  private final ProductDatabase productDatabase;

  @Autowired
  public DatabaseCartRepository(CartDatabase cartDatabase, CartItemDatabase cartItemDatabase,
      CustomerDatabase customerDatabase,
      ProductDatabase productDatabase) {
    this.cartDatabase = cartDatabase;
    this.cartItemDatabase = cartItemDatabase;
    this.customerDatabase = customerDatabase;
    this.productDatabase = productDatabase;
  }

  @Override
  public void create(Cart cart) {
    Optional<CustomerOrm> customerOrm = this.customerDatabase.findById(cart.customerId().toString());

    CartOrm cartOrm = new CartOrm(cart.id().toString(), customerOrm.get(), null);

    this.cartDatabase.save(cartOrm);

    for (CartItem item : cart.items()) {
      Optional<ProductOrm> productOrm = this.productDatabase.findById(item.productId().toString());

      CartItemOrm cartItemOrm = new CartItemOrm(item.id().toString(), cartOrm, productOrm.get(),
          item.quantity().quantity());

      this.cartItemDatabase.save(cartItemOrm);
    }
  }

  @Override
  public Optional<Cart> findOneByCustomerId(UUID customerId) {
    Optional<CartOrm> cartOrmFound = this.cartDatabase.findOneByCustomerId(customerId.toString());

    if (cartOrmFound.isEmpty()) {
      return Optional.empty();
    }

    ArrayList<CartItem> cartItems = new ArrayList<>();

    for (CartItemOrm cartItemOrm : cartOrmFound.get().cartItems()) {
      CartItem cartItem = CartItem.reconstitute(UUID.fromString(cartItemOrm.id()),
          UUID.fromString(cartItemOrm.product().id()), null, null);

      cartItems.add(cartItem);
    }

    Cart cart = Cart.reconstitute(UUID.fromString(cartOrmFound.get().id()),
        UUID.fromString(cartOrmFound.get().customer().id()),
        cartItems);

    return Optional.of(cart);

  }

  @Override
  public void update(Cart cart) {
    Optional<ProductOrm> productOrm = this.productDatabase.findById(cart.customerId().toString());
    Optional<CustomerOrm> customerOrm = this.customerDatabase.findById(cart.customerId().toString());
    ArrayList<CartItemOrm> cartItemsOrm = new ArrayList<>();

    for (CartItem item : cart.items()) {
      CartItemOrm cartItemOrm = new CartItemOrm(item.id().toString(), null, productOrm.get(),
          item.quantity().quantity());

      cartItemsOrm.add(cartItemOrm);
    }

    CartOrm cartOrm = new CartOrm(cart.id().toString(), customerOrm.get(), cartItemsOrm);
    this.cartDatabase.save(cartOrm);
  }
}
