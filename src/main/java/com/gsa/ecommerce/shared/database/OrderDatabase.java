package com.gsa.ecommerce.shared.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shared.database.orms.OrderOrm;

public interface OrderDatabase extends JpaRepository<OrderOrm, String> {

}
