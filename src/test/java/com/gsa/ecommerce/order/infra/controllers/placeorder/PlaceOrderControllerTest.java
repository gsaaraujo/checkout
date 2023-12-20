package com.gsa.ecommerce.order.infra.controllers.placeorder;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.gsa.ecommerce.shared.database.CartDatabase;
import com.gsa.ecommerce.shared.database.orms.CartOrm;
import com.gsa.ecommerce.shared.database.OrderDatabase;
import com.gsa.ecommerce.shared.database.ProductDatabase;
import com.gsa.ecommerce.shared.database.orms.ProductOrm;
import com.gsa.ecommerce.shared.database.CartItemDatabase;
import com.gsa.ecommerce.shared.database.CustomerDatabase;
import com.gsa.ecommerce.shared.database.orms.CartItemOrm;
import com.gsa.ecommerce.shared.database.orms.CustomerOrm;
import com.gsa.ecommerce.shared.database.OrderItemDatabase;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PlaceOrderControllerTest {
  private final MockMvc mockMvc;
  private final ProductDatabase productDatabase;
  private final CustomerDatabase customerDatabase;
  private final CartDatabase cartDatabase;
  private final CartItemDatabase cartItemDatabase;
  private final OrderItemDatabase orderItemDatabase;
  private final OrderDatabase orderDatabase;

  @Autowired
  public PlaceOrderControllerTest(MockMvc mockMvc, ProductDatabase productDatabase,
      CustomerDatabase customerDatabase, CartDatabase cartDatabase, CartItemDatabase cartItemDatabase,
      OrderItemDatabase orderItemDatabase, OrderDatabase orderDatabase) {
    this.mockMvc = mockMvc;
    this.productDatabase = productDatabase;
    this.customerDatabase = customerDatabase;
    this.cartDatabase = cartDatabase;
    this.cartItemDatabase = cartItemDatabase;
    this.orderItemDatabase = orderItemDatabase;
    this.orderDatabase = orderDatabase;
  }

  @BeforeEach
  public void beforeEach() {
    this.orderItemDatabase.deleteAll();
    this.cartItemDatabase.deleteAll();
    this.orderDatabase.deleteAll();
    this.cartDatabase.deleteAll();
    this.customerDatabase.deleteAll();
    this.productDatabase.deleteAll();
  }

  @Test
  public void it_should_succeed_and_place_order() throws Exception {
    ProductOrm productOrm = new ProductOrm(UUID.randomUUID().toString(),
        "any_name", "any_description", 24.40);
    CustomerOrm customerOrm = new CustomerOrm("438da73c-789c-4558-b66c-3f780695fc25", "any_customer_name", null);
    CartOrm cartOrm = new CartOrm("69b4c0c7-552b-435a-b522-e347a1da07f5", customerOrm, null);
    CartItemOrm cartItemOrm = new CartItemOrm(UUID.randomUUID().toString(), cartOrm, productOrm, 4);
    this.productDatabase.save(productOrm);
    this.customerDatabase.save(customerOrm);
    this.cartDatabase.save(cartOrm);
    this.cartItemDatabase.save(cartItemOrm);

    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "cartId": "69b4c0c7-552b-435a-b522-e347a1da07f5",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25"
            }
            """))
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andExpect(MockMvcResultMatchers.content().string(""));
  }

  @Test
  public void it_should_fail_if_cart_id_is_not_provided() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25"
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  public void it_should_fail_if_customer_id_is_not_provided() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "cartId": "69b4c0c7-552b-435a-b522-e347a1da07f5"
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void it_should_fail_if_customer_is_not_found() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "cartId": "69b4c0c7-552b-435a-b522-e347a1da07f5",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25"
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void it_should_fail_if_cart_is_not_found() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/orders")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "cartId": "69b4c0c7-552b-435a-b522-e347a1da07f5",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25"
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
