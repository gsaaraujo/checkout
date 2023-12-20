package com.gsa.ecommerce.shared.database.orms;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity(name = "customers")
public class CustomerOrm {
  @Id
  String id;

  @Column(name = "name")
  String name;

  @OneToOne(mappedBy = "customer")
  CartOrm cart;

  public CustomerOrm() {
  }

  public CustomerOrm(String id, String name, CartOrm cart) {
    this.id = id;
    this.name = name;
    this.cart = cart;
  }

  public String id() {
    return id;
  }

  public String name() {
    return name;
  }

  public CartOrm cart() {
    return cart;
  }

}
