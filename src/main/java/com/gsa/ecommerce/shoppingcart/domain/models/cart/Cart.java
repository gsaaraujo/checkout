package com.gsa.ecommerce.shoppingcart.domain.models.cart;

import java.util.UUID;
import java.util.ArrayList;
import java.math.BigDecimal;

public final class Cart {
  private final UUID id;
  private UUID customerId;
  private ArrayList<CartItem> items;

  private Cart(UUID id, UUID customerId, ArrayList<CartItem> items) {
    this.id = id;
    this.customerId = customerId;
    this.items = items;
  }

  public static Cart create(UUID customerId) {
    return new Cart(UUID.randomUUID(), customerId, new ArrayList<CartItem>());
  }

  public static Cart reconstitute(UUID id, UUID customerId, ArrayList<CartItem> items) {
    return new Cart(id, customerId, items);
  }

  public void addItem(CartItem item) {
    items.add(item);
  }

  public void removeItem(UUID itemId) {
    items.removeIf(item -> item.id().equals(itemId));
  }

  public BigDecimal totalPrice() {
    var totalPrice = new BigDecimal(0);

    for (CartItem item : items) {
      totalPrice = totalPrice.add(item.totalPrice().amount());
    }

    return totalPrice;
  }

  public UUID id() {
    return id;
  }

  public UUID customerId() {
    return customerId;
  }

  public ArrayList<CartItem> items() {
    return items;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cart other = (Cart) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (customerId == null) {
      if (other.customerId != null)
        return false;
    } else if (!customerId.equals(other.customerId))
      return false;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }
}
