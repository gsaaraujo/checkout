package com.gsa.ecommerce.order.infra.gateways.order;

import java.util.ArrayList;

import com.gsa.ecommerce.order.application.gateways.OrderGateway;

public class FakeOrderGateway implements OrderGateway {
  public ArrayList<OrderDTO> orders;

  public FakeOrderGateway() {
    this.orders = new ArrayList<OrderDTO>();
  }

  @Override
  public void create(OrderDTO orderDTO) {
    this.orders.add(orderDTO);
  }
}
