package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.math.BigDecimal;

import com.gsa.ecommerce.shoppingcart.domain.models.Money;

public final class CartItem {
  private final UUID id;
  private UUID productId;
  private Money price;
  private CartItemQuantity quantity;

  private CartItem(UUID id, UUID productId, Money price, CartItemQuantity quantity) {
    this.id = id;
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
  }

  public static CartItem create(UUID productId, Money price, CartItemQuantity quantity) {
    return new CartItem(UUID.randomUUID(), productId, price, quantity);
  }

  public static CartItem reconstitute(UUID id, UUID productId, Money price, CartItemQuantity quantity) {
    return new CartItem(id, productId, price, quantity);
  }

  public Money totalPrice() {
    BigDecimal money = price.amount().multiply(BigDecimal.valueOf(quantity.quantity()));
    return Money.reconstitute(money);
  }

  public UUID id() {
    return id;
  }

  public UUID productId() {
    return productId;
  }

  public Money price() {
    return price;
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
    if (price == null) {
      if (other.price != null)
        return false;
    } else if (!price.equals(other.price))
      return false;
    if (quantity == null) {
      if (other.quantity != null)
        return false;
    } else if (!quantity.equals(other.quantity))
      return false;
    return true;
  }

}
