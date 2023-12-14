package com.gsa.ecommerce.shoppingcart.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shoppingcart.infra.database.orms.CustomerOrm;

public interface CustomerDatabase extends JpaRepository<CustomerOrm, String> {

}
