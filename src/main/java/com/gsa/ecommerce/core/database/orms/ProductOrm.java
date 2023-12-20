package com.gsa.ecommerce.core.database.orms;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "products")
public class ProductOrm {
  @Id
  String id;

  @Column
  String name;

  @Column
  String description;

  @Column
  double price;

  public ProductOrm() {
  }

  public ProductOrm(String id, String name, String description, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String id() {
    return id;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public double price() {
    return price;
  }
}
