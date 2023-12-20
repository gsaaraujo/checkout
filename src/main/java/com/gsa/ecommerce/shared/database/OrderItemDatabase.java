package com.gsa.ecommerce.shared.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shared.database.orms.OrderItemOrm;

public interface OrderItemDatabase extends JpaRepository<OrderItemOrm, String> {

}
