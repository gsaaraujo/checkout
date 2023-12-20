package com.gsa.ecommerce.shared.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shared.database.orms.CartOrm;

public interface CartDatabase extends JpaRepository<CartOrm, String> {
  Optional<CartOrm> findOneByCustomerId(String customerId);
}
