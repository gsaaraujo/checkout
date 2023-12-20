package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.math.BigDecimal;

import com.gsa.ecommerce.core.exceptions.ValidationException;
import com.gsa.ecommerce.shoppingcart.domain.models.Money;

public final class CartItem {
  private final UUID id;
  private UUID productId;
  private Money unitPrice;
  private CartItemQuantity quantity;

  private CartItem(UUID id, UUID productId, Money unitPrice, CartItemQuantity quantity) {
    this.id = id;
    this.productId = productId;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
  }

  public static CartItem create(UUID productId, Money unitPrice, CartItemQuantity quantity) {
    return new CartItem(UUID.randomUUID(), productId, unitPrice, quantity);
  }

  public static CartItem reconstitute(UUID id, UUID productId, Money unitPrice, CartItemQuantity quantity) {
    return new CartItem(id, productId, unitPrice, quantity);
  }

  public Money totalPrice() throws ValidationException {
    BigDecimal money = unitPrice.amount().multiply(new BigDecimal(quantity.quantity()));
    return Money.create(money);
  }

  public UUID id() {
    return id;
  }

  public UUID productId() {
    return productId;
  }

  public Money unitPrice() {
    return unitPrice;
  }

  public CartItemQuantity quantity() {
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
    CartItem other = (CartItem) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (productId == null) {
      if (other.productId != null)
        return false;
    } else if (!productId.equals(other.productId))
      return false;
    if (unitPrice == null) {
      if (other.unitPrice != null)
        return false;
    } else if (!unitPrice.equals(other.unitPrice))
      return false;
    if (quantity == null) {
      if (other.quantity != null)
        return false;
    } else if (!quantity.equals(other.quantity))
      return false;
    return true;
  }

}
