package com.gsa.ecommerce.order.infra.gateways.order;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Component;
import com.gsa.ecommerce.shared.database.OrderDatabase;
import com.gsa.ecommerce.shared.database.orms.OrderOrm;
import com.gsa.ecommerce.shared.database.ProductDatabase;
import com.gsa.ecommerce.shared.database.orms.ProductOrm;
import com.gsa.ecommerce.shared.database.orms.CustomerOrm;
import com.gsa.ecommerce.shared.database.CustomerDatabase;
import com.gsa.ecommerce.shared.database.OrderItemDatabase;
import com.gsa.ecommerce.shared.database.orms.OrderItemOrm;
import com.gsa.ecommerce.order.application.gateways.OrderGateway;

@Component
public class DatabaseOrderGateway implements OrderGateway {

  private final OrderDatabase orderDatabase;
  private final OrderItemDatabase orderItemDatabase;
  private final ProductDatabase productDatabase;
  private final CustomerDatabase customerDatabase;

  public DatabaseOrderGateway(OrderDatabase orderDatabase, OrderItemDatabase orderItemDatabase,
      ProductDatabase productDatabase,
      CustomerDatabase customerDatabase) {
    this.orderDatabase = orderDatabase;
    this.orderItemDatabase = orderItemDatabase;
    this.productDatabase = productDatabase;
    this.customerDatabase = customerDatabase;
  }

  @Override
  public void create(OrderDTO orderDTO) {
    Optional<CustomerOrm> customerOrmFound = this.customerDatabase.findById(orderDTO.customerId().toString());

    OrderOrm orderOrm = new OrderOrm(UUID.randomUUID().toString(), customerOrmFound.get(), null);
    this.orderDatabase.save(orderOrm);

    for (OrderItemDTO orderItemDTO : orderDTO.items()) {
      Optional<ProductOrm> productOrmFound = this.productDatabase.findById(orderItemDTO.productId().toString());

      OrderItemOrm orderItemOrm = new OrderItemOrm(orderItemDTO.id().toString(), orderOrm, productOrmFound.get(),
          orderItemDTO.quantity(), orderItemDTO.price().doubleValue());

      this.orderItemDatabase.save(orderItemOrm);
    }
  }
}
