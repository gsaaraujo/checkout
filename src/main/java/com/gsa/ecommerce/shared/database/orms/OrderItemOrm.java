package com.gsa.ecommerce.shared.database.orms;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity(name = "order_items")
public class OrderItemOrm {
  @Id
  String id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  OrderOrm order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  ProductOrm product;

  @Column
  int quantity;

  @Column
  double price;

  public OrderItemOrm() {
  }

  public OrderItemOrm(String id, OrderOrm order, ProductOrm product, int quantity, double price) {
    this.id = id;
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public String id() {
    return id;
  }

  public OrderOrm order() {
    return order;
  }

  public ProductOrm product() {
    return product;
  }

  public int quantity() {
    return quantity;
  }

  public double price() {
    return price;
  }
}
