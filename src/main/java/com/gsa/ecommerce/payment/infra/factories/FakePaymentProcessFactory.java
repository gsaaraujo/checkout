package com.gsa.ecommerce.payment.infra.factories;

import com.gsa.ecommerce.payment.application.factories.PaymentProcessFactory;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.FakePixPaymentProcess;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.FakeBoletoPaymentProcess;
import com.gsa.ecommerce.payment.application.gateways.PaymentProcessGateway.PaymentProcess;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.FakeCreditCardPaymentProcess;

public class FakePaymentProcessFactory implements PaymentProcessFactory {

  @Override
  public PaymentProcess create(Options options) {
    switch (options) {
      case PIX:
        return new FakePixPaymentProcess();
      case BOLETO:
        return new FakeBoletoPaymentProcess();
      case CREDIT_CARD:
        return new FakeCreditCardPaymentProcess();
      default:
        throw new RuntimeException("Invalid option");
    }
  }

}
