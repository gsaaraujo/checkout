package com.gsa.ecommerce.order.application.usecases;

import java.util.UUID;
import java.util.Arrays;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gsa.ecommerce.order.infra.gateways.cart.FakeCartGateway;
import com.gsa.ecommerce.order.infra.gateways.customer.FakeCustomerGateway;
import com.gsa.ecommerce.order.infra.gateways.order.FakeOrderGateway;
import com.gsa.ecommerce.core.infra.messagequeue.FakeMessageQueue;
import com.gsa.ecommerce.order.application.gateways.CartGateway.CartDTO;
import com.gsa.ecommerce.order.application.gateways.CartGateway.CartItemDTO;
import com.gsa.ecommerce.order.application.gateways.CustomerGateway.CustomerDTO;

public class PlaceOrderTest {
  private PlaceOrder placeOrder;
  private FakeCartGateway fakeCartGateway;
  private FakeCustomerGateway fakeCustomerGateway;
  private FakeOrderGateway fakeOrderGateway;
  private FakeMessageQueue fakeMessageQueue;

  @BeforeEach
  public void beforeEach() {
    fakeCartGateway = new FakeCartGateway();
    fakeCustomerGateway = new FakeCustomerGateway();
    fakeOrderGateway = new FakeOrderGateway();
    fakeMessageQueue = new FakeMessageQueue();
    placeOrder = new PlaceOrder(fakeCartGateway, fakeCustomerGateway, fakeOrderGateway, fakeMessageQueue);
  }

  @Test
  public void it_should_succeed_and_place_order() {
    try {
      CartItemDTO cartItemDTO = new CartItemDTO(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal(24.40), 1);
      ArrayList<CartItemDTO> cartItemsDTO = new ArrayList<CartItemDTO>(Arrays.asList(cartItemDTO));
      CartDTO cartDTO = new CartDTO(UUID.fromString("6f617d4b-5768-4e4f-aa20-33638baa3b53"), cartItemsDTO);
      CustomerDTO customerDTO = new CustomerDTO(UUID.fromString("e5b57e59-a933-48c7-89e0-dcf9a0830e8e"));
      this.fakeCartGateway.carts.add(cartDTO);
      this.fakeCustomerGateway.customers.add(customerDTO);
      PlaceOrder.Input input = new PlaceOrder.Input(UUID.randomUUID(),
          UUID.fromString("6f617d4b-5768-4e4f-aa20-33638baa3b53"));

      placeOrder.execute(input);

      assertEquals(1, fakeOrderGateway.orders.size());
      assertEquals(1, fakeMessageQueue.messages.size());
    } catch (Exception exception) {
      //
    }
  }

  @Test
  public void it_should_fail_if_no_customer_is_found() {
    try {
      CartItemDTO cartItemDTO = new CartItemDTO(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal(24.40), 1);
      ArrayList<CartItemDTO> cartItemsDTO = new ArrayList<CartItemDTO>(Arrays.asList(cartItemDTO));
      CartDTO cartDTO = new CartDTO(UUID.fromString("6f617d4b-5768-4e4f-aa20-33638baa3b53"), cartItemsDTO);
      this.fakeCartGateway.carts.add(cartDTO);
      PlaceOrder.Input input = new PlaceOrder.Input(UUID.randomUUID(),
          UUID.fromString("6f617d4b-5768-4e4f-aa20-33638baa3b53"));

      placeOrder.execute(input);
    } catch (Exception exception) {
      assertEquals("Customer not found", exception.getMessage());
    }
  }

  @Test
  public void it_should_fail_if_no_cart_is_found() {
    try {
      CustomerDTO customerDTO = new CustomerDTO(UUID.fromString("e5b57e59-a933-48c7-89e0-dcf9a0830e8e"));
      this.fakeCustomerGateway.customers.add(customerDTO);
      PlaceOrder.Input input = new PlaceOrder.Input(UUID.fromString("e5b57e59-a933-48c7-89e0-dcf9a0830e8e"),
          UUID.randomUUID());

      placeOrder.execute(input);
    } catch (Exception exception) {
      assertEquals("Cart not found", exception.getMessage());
    }
  }
}
