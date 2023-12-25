package com.gsa.ecommerce.payment.infra.gateways.paymentprocess;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway;

@Component
public class StripePaymentProcessGateway implements PaymentProcessGateway {

  @Override
  public void process(PaymentProcess paymentProcess) {
    //
  }

}
