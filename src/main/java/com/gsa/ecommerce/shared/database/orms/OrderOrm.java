package com.gsa.ecommerce.shared.database.orms;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity(name = "orders")
public class OrderOrm {
  @Id
  String id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  CustomerOrm customer;

  @OneToMany(mappedBy = "order")
  List<OrderItemOrm> items;

  public OrderOrm() {
  }

  public OrderOrm(String id, CustomerOrm customer, List<OrderItemOrm> items) {
    this.id = id;
    this.customer = customer;
    this.items = items;
  }

  public String id() {
    return id;
  }

  public CustomerOrm customer() {
    return customer;
  }

  public List<OrderItemOrm> items() {
    return items;
  }

}
