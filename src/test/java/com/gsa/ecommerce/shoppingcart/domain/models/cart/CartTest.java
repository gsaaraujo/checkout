package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gsa.ecommerce.shoppingcart.domain.models.Money;

public class CartTest {
  @Test
  public void it_should_create_cart() {
    try {
      Cart sut = Cart.create(UUID.fromString("23c70aee-1e7c-44aa-af67-5e13504691c2"));

      assertAll(
          () -> assertEquals(UUID.fromString("23c70aee-1e7c-44aa-af67-5e13504691c2"), sut.customerId()),
          () -> assertEquals(0, sut.items().size()));
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_reconstitute_cart() {
    Cart sut = Cart.reconstitute(UUID.fromString("9232e5f7-c773-4eb9-bd42-fe95a11e8592"),
        UUID.fromString("23c70aee-1e7c-44aa-af67-5e13504691c2"), new ArrayList<CartItem>());

    assertAll(
        () -> assertEquals(UUID.fromString("9232e5f7-c773-4eb9-bd42-fe95a11e8592"), sut.id()),
        () -> assertEquals(UUID.fromString("23c70aee-1e7c-44aa-af67-5e13504691c2"), sut.customerId()),
        () -> assertEquals(0, sut.items().size()));
  }

  @Test
  public void it_should_add_items_of_the_same_product() {
    try {
      Cart sut = Cart.create(UUID.randomUUID());
      Money money = Money.create(new BigDecimal(2.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(4);
      CartItem cartItem = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);

      sut.addItem(cartItem);
      sut.addItem(cartItem);
      sut.addItem(cartItem);

      assertAll(
          () -> assertEquals(1, sut.items().size()),
          () -> assertEquals(12, sut.totalQuantityOfItems()),
          () -> assertEquals(30, sut.totalPrice().doubleValue()));
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_add_items_of_different_products() {
    try {
      Cart sut = Cart.create(UUID.randomUUID());
      Money money = Money.create(new BigDecimal(2.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(4);
      CartItem cartItem1 = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);
      CartItem cartItem2 = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);
      CartItem cartItem3 = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);

      sut.addItem(cartItem1);
      sut.addItem(cartItem2);
      sut.addItem(cartItem3);

      assertAll(
          () -> assertEquals(3, sut.items().size()),
          () -> assertEquals(12, sut.totalQuantityOfItems()),
          () -> assertEquals(30, sut.totalPrice().doubleValue()));
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_add_items_of_mix_products() {
    try {
      Cart sut = Cart.create(UUID.randomUUID());
      Money money = Money.create(new BigDecimal(2.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(4);
      CartItem cartItem1 = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);
      CartItem cartItem2 = CartItem.create(UUID.randomUUID(), money, cartItemQuantity);

      sut.addItem(cartItem1);
      sut.addItem(cartItem2);
      sut.addItem(cartItem2);
      sut.addItem(cartItem2);

      assertAll(
          () -> assertEquals(2, sut.items().size()),
          () -> assertEquals(16, sut.totalQuantityOfItems()),
          () -> assertEquals(40, sut.totalPrice().doubleValue()));

    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_remove_a_item() {
    try {
      Cart sut = Cart.create(UUID.fromString("23c70aee-1e7c-44aa-af67-5e13504691c2"));
      Money money = Money.create(new BigDecimal(5.50));
      CartItemQuantity cartItemQuantity = CartItemQuantity.create(10);
      CartItem cartItem1 = CartItem.create(UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), money,
          cartItemQuantity);
      CartItem cartItem2 = CartItem.create(UUID.fromString("1d95c593-8e61-479e-a1c3-8ef1cdfe4f02"), money,
          cartItemQuantity);

      sut.addItem(cartItem1);
      sut.addItem(cartItem2);
      sut.removeItem(cartItem2.id());

      assertEquals(1, sut.items().size());
    } catch (Exception e) {
      //
    }
  }
}
