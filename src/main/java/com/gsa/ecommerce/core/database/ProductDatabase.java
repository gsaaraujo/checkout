package com.gsa.ecommerce.core.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.database.orms.ProductOrm;

public interface ProductDatabase extends JpaRepository<ProductOrm, String> {
}
