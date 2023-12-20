package com.gsa.ecommerce.shared.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.shared.database.orms.CustomerOrm;

public interface CustomerDatabase extends JpaRepository<CustomerOrm, String> {

}
