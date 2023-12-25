package com.gsa.ecommerce.payment.infra.gateways.paymentprocess;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway.PaymentProcess;

@Component
public class BoletoPaymentProcess implements PaymentProcess {

  @Override
  public void pay() {
    //
  }

}
