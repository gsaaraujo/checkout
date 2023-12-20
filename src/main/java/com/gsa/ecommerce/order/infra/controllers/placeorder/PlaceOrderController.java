package com.gsa.ecommerce.order.infra.controllers.placeorder;

import java.util.UUID;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsa.ecommerce.core.http.ErrorHttpResponse;
import com.gsa.ecommerce.order.application.usecases.PlaceOrder;

@RestController
@RequestMapping("/orders")
public class PlaceOrderController {
  private final PlaceOrder placeOrder;

  public PlaceOrderController(PlaceOrder placeOrder) {
    this.placeOrder = placeOrder;
  }

  @PostMapping()
  public ResponseEntity<?> handle(@RequestBody @Valid PlaceOrderInput input) {
    try {
      PlaceOrder.Input placeOrderInput = new PlaceOrder.Input(
          UUID.fromString(input.customerId()),
          UUID.fromString(input.cartId()));

      placeOrder.execute(placeOrderInput);
      return ResponseEntity.noContent().build();
    } catch (Exception exception) {
      if (exception.getMessage().equals("Customer not found")) {
        ErrorHttpResponse error = new ErrorHttpResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
      }

      if (exception.getMessage().equals("Cart not found")) {
        ErrorHttpResponse error = new ErrorHttpResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
      }

      exception.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }
}
