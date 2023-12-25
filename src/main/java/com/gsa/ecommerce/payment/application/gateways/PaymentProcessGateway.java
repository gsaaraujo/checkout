package com.gsa.ecommerce.payment.application.gateways;

public interface PaymentProcessGateway {
  public interface PaymentProcess {
    public void pay();
  }

  public void process(PaymentProcess paymentProcess);
}
