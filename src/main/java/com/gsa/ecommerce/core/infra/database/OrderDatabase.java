package com.gsa.ecommerce.core.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.infra.database.orms.OrderOrm;

public interface OrderDatabase extends JpaRepository<OrderOrm, String> {

}
