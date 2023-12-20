package com.gsa.ecommerce.order.infra.gateways.customer;

import java.util.UUID;
import java.util.ArrayList;

import com.gsa.ecommerce.order.application.gateways.CustomerGateway;

public class FakeCustomerGateway implements CustomerGateway {
  public ArrayList<CustomerDTO> customers;

  public FakeCustomerGateway() {
    this.customers = new ArrayList<CustomerDTO>();
  }

  @Override
  public boolean exists(UUID customerId) {
    return this.customers.stream().filter(customer -> customer.id().equals(customerId)).findFirst().isPresent();
  }

}
