package com.gsa.ecommerce.shared.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shared.database.orms.ProductOrm;

public interface ProductDatabase extends JpaRepository<ProductOrm, String> {
}
