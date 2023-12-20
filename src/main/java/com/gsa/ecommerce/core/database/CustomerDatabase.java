package com.gsa.ecommerce.core.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.database.orms.CustomerOrm;

public interface CustomerDatabase extends JpaRepository<CustomerOrm, String> {

}
