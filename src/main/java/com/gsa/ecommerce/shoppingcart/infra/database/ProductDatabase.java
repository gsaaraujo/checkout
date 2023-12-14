package com.gsa.ecommerce.shoppingcart.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shoppingcart.infra.database.orms.ProductOrm;

public interface ProductDatabase extends JpaRepository<ProductOrm, String> {
}
