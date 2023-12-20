package com.gsa.ecommerce.order.application.usecases;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.core.infra.exceptions.DomainException;
import com.gsa.ecommerce.core.infra.messagequeue.MessageQueue;
import com.gsa.ecommerce.order.application.gateways.CartGateway;
import com.gsa.ecommerce.order.application.gateways.OrderGateway;
import com.gsa.ecommerce.order.application.gateways.CustomerGateway;
import com.gsa.ecommerce.order.application.gateways.CartGateway.CartDTO;
import com.gsa.ecommerce.order.application.gateways.OrderGateway.OrderDTO;
import com.gsa.ecommerce.order.application.gateways.OrderGateway.OrderItemDTO;

@Component
public class PlaceOrder {
  public record Input(UUID customerId, UUID cartId) {
  }

  private final CartGateway cartGateway;
  private final CustomerGateway customerGateway;
  private final OrderGateway orderGateway;
  private final MessageQueue messageQueue;

  public PlaceOrder(CartGateway cartGateway, CustomerGateway customerGateway, OrderGateway orderGateway,
      MessageQueue messageQueue) {
    this.cartGateway = cartGateway;
    this.customerGateway = customerGateway;
    this.orderGateway = orderGateway;
    this.messageQueue = messageQueue;
  }

  public void execute(Input input) throws DomainException {
    if (!this.customerGateway.exists(input.customerId())) {
      throw new DomainException("Customer not found");
    }

    Optional<CartDTO> cartDTO = this.cartGateway.findOneById(input.cartId());

    if (cartDTO.isEmpty()) {
      throw new DomainException("Cart not found");
    }

    ArrayList<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();

    for (CartGateway.CartItemDTO cartItem : cartDTO.get().items()) {
      orderItems.add(new OrderItemDTO(UUID.randomUUID(), cartItem.productId(), cartItem.price(), cartItem.quantity()));
    }

    OrderDTO orderDTO = new OrderGateway.OrderDTO(UUID.randomUUID(), input.customerId(), orderItems);
    this.orderGateway.create(orderDTO);
    this.messageQueue.publish("paymentProcess", orderDTO.id().toString());
  }
}
