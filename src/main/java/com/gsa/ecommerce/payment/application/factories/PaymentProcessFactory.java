package com.gsa.ecommerce.payment.application.factories;

import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway.PaymentProcess;

public interface PaymentProcessFactory {
  public enum Options {
    PIX,
    BOLETO,
    CREDIT_CARD
  }

  public PaymentProcess create(Options options);
}
