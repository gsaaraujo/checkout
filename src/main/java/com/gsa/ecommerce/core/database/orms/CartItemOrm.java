package com.gsa.ecommerce.core.database.orms;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity(name = "cart_items")
public class CartItemOrm {
  @Id
  String id;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  CartOrm cart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  ProductOrm product;

  @Column(name = "quantity")
  int quantity;

  public CartItemOrm() {
  }

  public CartItemOrm(String id, CartOrm cart, ProductOrm product, int quantity) {
    this.id = id;
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }

  public String id() {
    return id;
  }

  public CartOrm cart() {
    return cart;
  }

  public ProductOrm product() {
    return product;
  }

  public int quantity() {
    return quantity;
  }
}
