package com.gsa.ecommerce.payment.infra.gateways.paymentprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway;

public class FakePaymentProcessGateway implements PaymentProcessGateway {
  public record Payment(UUID id) {
  }

  public List<Payment> payments;

  public FakePaymentProcessGateway() {
    this.payments = new ArrayList<Payment>();
  }

  @Override
  public void process(PaymentProcess paymentProcess) {
    this.payments.add(new Payment(UUID.randomUUID()));
  }

  public List<Payment> payments() {
    return this.payments;
  }

}
