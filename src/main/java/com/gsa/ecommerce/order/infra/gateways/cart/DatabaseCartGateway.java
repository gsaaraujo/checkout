package com.gsa.ecommerce.order.infra.gateways.cart;

import java.util.UUID;

import java.util.Optional;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.core.database.CartDatabase;
import com.gsa.ecommerce.core.database.orms.CartItemOrm;
import com.gsa.ecommerce.core.database.orms.CartOrm;
import com.gsa.ecommerce.order.application.gateways.CartGateway;

@Component
public class DatabaseCartGateway implements CartGateway {
  private final CartDatabase cartDatabase;

  public DatabaseCartGateway(CartDatabase cartDatabase) {
    this.cartDatabase = cartDatabase;
  }

  @Override
  public Optional<CartDTO> findOneById(UUID id) {
    Optional<CartOrm> cartOrmFound = this.cartDatabase.findById(id.toString());

    if (cartOrmFound.isEmpty()) {
      return Optional.empty();
    }

    ArrayList<CartItemDTO> cartItemsDTO = new ArrayList<>();

    for (CartItemOrm cartItemOrm : cartOrmFound.get().cartItems()) {
      CartItemDTO cartItemDTO = new CartItemDTO(UUID.fromString(cartItemOrm.id()),
          UUID.fromString(cartItemOrm.product().id()),
          new BigDecimal(cartItemOrm.product().price()), cartItemOrm.quantity());

      cartItemsDTO.add(cartItemDTO);
    }

    CartDTO cartDTO = new CartDTO(UUID.fromString(cartOrmFound.get().id()), cartItemsDTO);
    return Optional.of(cartDTO);
  }
}
