package com.gsa.ecommerce.shared.database.orms;

import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity(name = "carts")
public class CartOrm {
  @Id
  String id;

  @OneToOne
  @JoinColumn(name = "customer_id")
  CustomerOrm customer;

  @OneToMany(mappedBy = "cart")
  List<CartItemOrm> cartItems;

  public CartOrm() {
  }

  public CartOrm(String id, CustomerOrm customer, List<CartItemOrm> cartItems) {
    this.id = id;
    this.customer = customer;
    this.cartItems = cartItems;
  }

  public String id() {
    return id;
  }

  public CustomerOrm customer() {
    return customer;
  }

  public List<CartItemOrm> cartItems() {
    return cartItems;
  }
}
