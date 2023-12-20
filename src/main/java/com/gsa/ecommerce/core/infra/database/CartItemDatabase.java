package com.gsa.ecommerce.core.infra.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.infra.database.orms.CartItemOrm;

public interface CartItemDatabase extends JpaRepository<CartItemOrm, String> {
  List<CartItemOrm> findAllByCartId(String cartId);
}
