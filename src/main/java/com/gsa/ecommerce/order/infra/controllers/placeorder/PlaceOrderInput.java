package com.gsa.ecommerce.order.infra.controllers.placeorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PlaceOrderInput {
  @NotNull(message = "customerId is required")
  @NotBlank(message = "customerId is required")
  @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$", message = "customerId must be a valid UUID")
  private final String customerId;

  @NotNull(message = "cartId is required")
  @NotBlank(message = "cartId is required")
  @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$", message = "cartId must be a valid UUID")
  private final String cartId;

  public PlaceOrderInput(String customerId, String cartId) {
    this.customerId = customerId;
    this.cartId = cartId;
  }

  public String customerId() {
    return customerId;
  }

  public String cartId() {
    return cartId;
  }

}
