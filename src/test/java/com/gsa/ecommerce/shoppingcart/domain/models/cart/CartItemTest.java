package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gsa.ecommerce.shoppingcart.domain.models.Money;

public class CartItemTest {
  @Test
  public void it_should_create_cart_item() {
    try {
      Money money = Money.create(new BigDecimal(5.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(10);

      CartItem sut = CartItem.create(UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), money, cartItemQuantity);

      assertAll(
          () -> assertEquals(UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), sut.productId()),
          () -> assertEquals(new BigDecimal(5.50), sut.unitPrice().amount()));
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_reconstitute_cart_item() {
    Money money = Money.reconstitute(new BigDecimal(5.50));
    CartItemQuantity cartItemQuantity = CartItemQuantity.reconstitute(10);

    CartItem sut = CartItem.reconstitute(UUID.fromString("9220c937-b59e-42cd-a87c-a8fdb752484c"),
        UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), money, cartItemQuantity);

    assertAll(
        () -> assertEquals(UUID.fromString("9220c937-b59e-42cd-a87c-a8fdb752484c"), sut.id()),
        () -> assertEquals(UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), sut.productId()),
        () -> assertEquals(new BigDecimal(5.50), sut.unitPrice().amount()),
        () -> assertEquals(10, sut.quantity().quantity()));
  }

  @Test
  public void it_should_calculate_total_price_correctly() {
    try {
      Money money = Money.create(new BigDecimal(5.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(11);

      CartItem sut = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);

      assertEquals(new BigDecimal(60.5), sut.totalPrice().amount());
    } catch (Exception e) {
      //
    }
  }
}
