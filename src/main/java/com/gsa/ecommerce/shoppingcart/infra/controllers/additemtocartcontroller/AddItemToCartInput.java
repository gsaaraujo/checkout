package com.gsa.ecommerce.shoppingcart.infra.controllers.additemtocartcontroller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

public class AddItemToCartInput {
  @NotNull(message = "productId is required")
  @NotBlank(message = "productId is required")
  @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$", message = "productId must be a valid UUID")
  private String productId;

  @NotNull(message = "customerId is required")
  @NotBlank(message = "customerId is required")
  @Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$", message = "customerId must be a valid UUID")
  private String customerId;

  @NotNull(message = "quantity is required")
  private int quantity;

  public AddItemToCartInput(String productId, String customerId, int quantity) {
    this.productId = productId;
    this.customerId = customerId;
    this.quantity = quantity;
  }

  public String productId() {
    return productId;
  }

  public String customerId() {
    return customerId;
  }

  public int quantity() {
    return quantity;
  }
}