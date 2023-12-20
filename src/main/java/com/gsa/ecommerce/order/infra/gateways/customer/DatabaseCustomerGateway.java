package com.gsa.ecommerce.order.infra.gateways.customer;

import java.util.UUID;

import org.springframework.stereotype.Component;
import com.gsa.ecommerce.shared.database.CustomerDatabase;
import com.gsa.ecommerce.order.application.gateways.CustomerGateway;

@Component
public class DatabaseCustomerGateway implements CustomerGateway {
  private final CustomerDatabase customerDatabase;

  public DatabaseCustomerGateway(CustomerDatabase customerDatabase) {
    this.customerDatabase = customerDatabase;
  }

  @Override
  public boolean exists(UUID customerId) {
    return this.customerDatabase.existsById(customerId.toString());
  }

}
