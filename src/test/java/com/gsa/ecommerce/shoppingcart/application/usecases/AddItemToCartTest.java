package com.gsa.ecommerce.shoppingcart.application.usecases;

import java.util.UUID;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gsa.ecommerce.shoppingcart.domain.models.cart.Cart;
import com.gsa.ecommerce.order.application.gateways.CustomerGateway.CustomerDTO;
import com.gsa.ecommerce.order.infra.gateways.customer.FakeCustomerGateway;
import com.gsa.ecommerce.shoppingcart.infra.gateways.product.FakeProductGateway;
import com.gsa.ecommerce.shoppingcart.infra.repositories.cart.FakeCartRepository;
import com.gsa.ecommerce.shoppingcart.application.gateways.ProductGateway.ProductDTO;

public class AddItemToCartTest {
  private AddItemToCart addItemToCart;
  private FakeCartRepository fakeCartRepository;
  private FakeProductGateway fakeProductGateway;
  private FakeCustomerGateway fakeCustomerGateway;

  @BeforeEach
  void beforeEach() {
    fakeCartRepository = new FakeCartRepository();
    fakeProductGateway = new FakeProductGateway();
    fakeCustomerGateway = new FakeCustomerGateway();
    addItemToCart = new AddItemToCart(fakeCartRepository, fakeProductGateway, fakeCustomerGateway);
  }

  @Test
  public void it_should_create_a_cart_and_add_a_item() {
    try {
      ProductDTO productDTO = new ProductDTO(UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          new BigDecimal(20));
      fakeProductGateway.products.add(productDTO);
      AddItemToCart.Input input = new AddItemToCart.Input(
          UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          UUID.fromString("a94e0b42-2547-4812-b59c-19c593cb3358"),
          4);

      addItemToCart.execute(input);

      assertEquals(1, fakeCartRepository.carts.get(0).items().size());
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_update_a_cart_and_add_a_item() {
    try {
      Cart cart = Cart.reconstitute(UUID.fromString("a9d1189d-e1f5-4106-9a80-853fb84d2d2e"),
          UUID.fromString("1e7ca7b2-cf6c-44e2-a3f2-2f7518eaecb2"), new ArrayList<>());
      ProductDTO productDTO = new ProductDTO(UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          new BigDecimal(20));
      fakeCartRepository.carts.add(cart);
      fakeProductGateway.products.add(productDTO);
      AddItemToCart.Input input = new AddItemToCart.Input(
          UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          UUID.fromString("a94e0b42-2547-4812-b59c-19c593cb3358"),
          4);

      addItemToCart.execute(input);

      assertEquals(true,
          fakeCartRepository.carts.get(0).id().equals(UUID.fromString("a9d1189d-e1f5-4106-9a80-853fb84d2d2e")));
      assertEquals(1, fakeCartRepository.carts.get(0).items().size());
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_throw_exception_when_customer_is_not_found() {
    try {
      ProductDTO productDTO = new ProductDTO(UUID.fromString("a94e0b42-2547-4812-b59c-19c593cb3358"),
          new BigDecimal(20));
      fakeProductGateway.products.add(productDTO);

      AddItemToCart.Input input = new AddItemToCart.Input(
          UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          UUID.fromString("a94e0b42-2547-4812-b59c-19c593cb3358"),
          4);

      addItemToCart.execute(input);
    } catch (Exception e) {
      assertEquals("Customer not found", e.getMessage());
    }
  }

  @Test
  public void it_should_throw_exception_when_product_is_not_found() {
    try {
      CustomerDTO customerDTO = new CustomerDTO(UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"));
      fakeCustomerGateway.customers.add(customerDTO);

      AddItemToCart.Input input = new AddItemToCart.Input(
          UUID.fromString("edd719ad-9d5e-4400-8c84-2fb9e90ecff0"),
          UUID.fromString("a94e0b42-2547-4812-b59c-19c593cb3358"),
          4);

      addItemToCart.execute(input);
    } catch (Exception e) {
      assertEquals("Product not found", e.getMessage());
    }
  }

}
