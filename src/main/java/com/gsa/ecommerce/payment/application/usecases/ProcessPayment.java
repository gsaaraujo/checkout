package com.gsa.ecommerce.payment.application.usecases;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway;
import com.gsa.ecommerce.payment.application.factories.PaymentProcessFactory;
import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway.PaymentProcess;

@Component
public class ProcessPayment {
  public record Input(UUID orderId, Options option) {
  }

  public enum Options {
    PIX,
    BOLETO,
    CREDIT_CARD
  }

  private final PaymentProcessGateway paymentProcessGateway;
  private final PaymentProcessFactory paymentProcessFactory;

  public ProcessPayment(PaymentProcessGateway paymentProcessGateway, PaymentProcessFactory paymentProcessFactory) {
    this.paymentProcessGateway = paymentProcessGateway;
    this.paymentProcessFactory = paymentProcessFactory;
  }

  public void execute(Input input) {
    PaymentProcess paymentProcess = this.paymentProcessFactory
        .create(PaymentProcessFactory.Options.valueOf(input.option().toString()));

    this.paymentProcessGateway.process(paymentProcess);
  }
}
