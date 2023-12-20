package com.gsa.ecommerce.core.infra.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.ecommerce.core.infra.database.orms.CustomerOrm;

public interface CustomerDatabase extends JpaRepository<CustomerOrm, String> {

}
