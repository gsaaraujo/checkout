package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CartItemQuantityTest {
  @Test
  public void it_should_create_cart_item_quantity() {
    try {
      CartItemQuantity sut = CartItemQuantity.create(5);

      assertEquals(5, sut.quantity());
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_reconstitute_cart_item_quantity() {
    CartItemQuantity sut = CartItemQuantity.reconstitute(5);

    assertEquals(5, sut.quantity());
  }

  @Test
  public void it_should_fail_if_quantity_is_less_than_one() {
    try {
      CartItemQuantity.create(0);
    } catch (Exception e) {
      assertEquals("Quantity must be greater than 0", e.getMessage());
    }
  }
}
