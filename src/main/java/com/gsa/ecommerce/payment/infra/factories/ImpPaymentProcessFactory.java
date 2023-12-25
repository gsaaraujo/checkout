package com.gsa.ecommerce.payment.infra.factories;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.payment.application.factories.PaymentProcessFactory;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.PixPaymentProcess;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.BoletoPaymentProcess;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.CreditCardPaymentProcess;
import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway.PaymentProcess;

@Component
public class ImpPaymentProcessFactory implements PaymentProcessFactory {

  @Override
  public PaymentProcess create(Options options) {
    switch (options) {
      case PIX:
        return new PixPaymentProcess();
      case BOLETO:
        return new BoletoPaymentProcess();
      case CREDIT_CARD:
        return new CreditCardPaymentProcess();
      default:
        throw new RuntimeException("Invalid option");
    }
  }

}
