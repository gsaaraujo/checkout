package com.gsa.ecommerce.shoppingcart.infra.controllers.additemtocartcontroller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.gsa.ecommerce.shoppingcart.infra.database.orms.CartOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.CartDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.ProductOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.ProductDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.CartItemOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.orms.CustomerOrm;
import com.gsa.ecommerce.shoppingcart.infra.database.CartItemDatabase;
import com.gsa.ecommerce.shoppingcart.infra.database.CustomerDatabase;

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
}
