package com.gsa.ecommerce.shoppingcart.infra.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shoppingcart.infra.database.orms.CartItemOrm;

public interface CartItemDatabase extends JpaRepository<CartItemOrm, String> {
  List<CartItemOrm> findAllByCartId(String cartId);
}
