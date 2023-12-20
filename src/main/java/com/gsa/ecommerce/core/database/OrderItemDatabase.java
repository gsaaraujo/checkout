package com.gsa.ecommerce.core.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.database.orms.OrderItemOrm;

public interface OrderItemDatabase extends JpaRepository<OrderItemOrm, String> {

}
