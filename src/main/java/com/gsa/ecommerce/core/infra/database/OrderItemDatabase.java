package com.gsa.ecommerce.core.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.infra.database.orms.OrderItemOrm;

public interface OrderItemDatabase extends JpaRepository<OrderItemOrm, String> {

}
