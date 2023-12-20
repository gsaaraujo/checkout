package com.gsa.ecommerce.shoppingcart.infra.controllers.additemtocartcontroller;

import java.util.UUID;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsa.ecommerce.core.http.ErrorHttpResponse;
import com.gsa.ecommerce.shoppingcart.application.usecases.AddItemToCart;

@RestController
@RequestMapping("/carts")
public class AddItemToCartController {
  private final AddItemToCart addItemToCart;

  public AddItemToCartController(AddItemToCart addItemToCart) {
    this.addItemToCart = addItemToCart;
  }

  @ResponseBody()
  @PostMapping("/items/add")
  public ResponseEntity<?> handle(@RequestBody @Valid AddItemToCartInput input) {
    try {
      AddItemToCart.Input AddItemToCarInput = new AddItemToCart.Input(
          UUID.fromString(input.customerId()),
          UUID.fromString(input.productId()),
          input.quantity());

      addItemToCart.execute(AddItemToCarInput);
      return ResponseEntity.noContent().build();
    } catch (Exception exception) {
      if (exception.getMessage().equals("Product not found")) {
        ErrorHttpResponse error = new ErrorHttpResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
      }

      exception.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }
}
