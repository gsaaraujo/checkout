package com.gsa.ecommerce.shoppingcart.infra.controllers.additemtocart;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.gsa.ecommerce.core.infra.database.orms.CartOrm;
import com.gsa.ecommerce.core.infra.database.CartDatabase;
import com.gsa.ecommerce.core.infra.database.orms.ProductOrm;
import com.gsa.ecommerce.core.infra.database.ProductDatabase;
import com.gsa.ecommerce.core.infra.database.CartItemDatabase;
import com.gsa.ecommerce.core.infra.database.CustomerDatabase;
import com.gsa.ecommerce.core.infra.database.orms.CartItemOrm;
import com.gsa.ecommerce.core.infra.database.orms.CustomerOrm;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AddItemToCartControllerTest {
  private final MockMvc mockMvc;
  private final ProductDatabase productDatabase;
  private final CustomerDatabase customerDatabase;
  private final CartDatabase cartDatabase;
  private final CartItemDatabase cartItemDatabase;

  @Autowired
  public AddItemToCartControllerTest(MockMvc mockMvc, ProductDatabase productDatabase,
      CustomerDatabase customerDatabase, CartDatabase cartDatabase, CartItemDatabase cartItemDatabase) {
    this.mockMvc = mockMvc;
    this.productDatabase = productDatabase;
    this.customerDatabase = customerDatabase;
    this.cartDatabase = cartDatabase;
    this.cartItemDatabase = cartItemDatabase;
  }

  @BeforeEach
  public void setup() {
    this.cartItemDatabase.deleteAll();
    this.cartDatabase.deleteAll();
    this.productDatabase.deleteAll();
    this.customerDatabase.deleteAll();
  }

  @Test
  public void it_should_add_item_to_cart() throws Exception {
    ProductOrm productOrm = new ProductOrm("9b5b1c69-353b-4893-92d1-338a5d4c653a", "any_name", "any_description",
        24.40);
    CustomerOrm customerOrm = new CustomerOrm("438da73c-789c-4558-b66c-3f780695fc25", "any_customer_name", null);
    this.productDatabase.save(productOrm);
    this.customerDatabase.save(customerOrm);

    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": "9b5b1c69-353b-4893-92d1-338a5d4c653a",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25",
              "quantity": 4
            }
            """))
        .andExpect(MockMvcResultMatchers.status().isNoContent());

    Optional<CartOrm> cartOrm = this.cartDatabase.findOneByCustomerId("438da73c-789c-4558-b66c-3f780695fc25");
    List<CartItemOrm> cartItemsOrm = this.cartItemDatabase.findAllByCartId(cartOrm.get().id());

    assertAll(
        () -> assertTrue(cartOrm.isPresent()),
        () -> assertEquals(1, cartItemsOrm.size()),
        () -> assertTrue(cartItemsOrm.get(0).quantity() == 4));
  }

  @Test
  public void it_should_fail_if_product_is_not_found() throws Exception {
    CustomerOrm customerOrm = new CustomerOrm("438da73c-789c-4558-b66c-3f780695fc25", "any_customer_name", null);
    this.customerDatabase.save(customerOrm);

    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": "9b5b1c69-353b-4893-92d1-338a5d4c653a",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25",
              "quantity": 1
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Product not found"));
  }

  @Test
  public void it_should_fail_if_customer_is_not_found() throws Exception {
    ProductOrm productOrm = new ProductOrm("9b5b1c69-353b-4893-92d1-338a5d4c653a", "any_name", "any_description",
        24.40);
    this.productDatabase.save(productOrm);

    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": "9b5b1c69-353b-4893-92d1-338a5d4c653a",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25",
              "quantity": 1
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Customer not found"));
  }

  @Test
  public void it_should_fail_if_any_property_is_missing() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.productId").value("productId is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.customerId").value("customerId is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.quantity").value("quantity is required"));
  }

  @Test
  public void it_should_fail_if_any_property_is_empty() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": " ",
              "customerId": " ",
              "quantity": 4
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.productId").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.customerId").exists());
  }

  @Test
  public void it_should_fail_if_ids_are_not_uuid() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": "abc",
              "customerId": "abc",
              "quantity": 4
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.productId").value("productId must be a valid UUID"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.customerId").value("customerId must be a valid UUID"));
  }

  @Test
  public void it_should_fail_if_quantity_is_less_than_one() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/carts/items/add")
        .contentType(MediaType.APPLICATION_JSON).content("""
            {
              "productId": "9b5b1c69-353b-4893-92d1-338a5d4c653a",
              "customerId": "438da73c-789c-4558-b66c-3f780695fc25",
              "quantity": 0
            }
            """))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.errors.quantity").value("quantity must be greater than or equal to 1"));
  }
}
