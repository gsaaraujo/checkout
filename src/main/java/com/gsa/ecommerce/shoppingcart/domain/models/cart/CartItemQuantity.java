package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import com.gsa.ecommerce.core.exceptions.ValidationException;

public final class CartItemQuantity {
  private final int quantity;

  private CartItemQuantity(int quantity) {
    this.quantity = quantity;
  }

  public static CartItemQuantity create(int quantity) throws ValidationException {
    if (quantity < 1) {
      throw new ValidationException("Quantity must be greater than 0");
    }

    return new CartItemQuantity(quantity);
  }

  public static CartItemQuantity reconstitute(int quantity) {
    return new CartItemQuantity(quantity);
  }

  public int quantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CartItemQuantity other = (CartItemQuantity) obj;
    if (quantity != other.quantity)
      return false;
    return true;
  }
}
