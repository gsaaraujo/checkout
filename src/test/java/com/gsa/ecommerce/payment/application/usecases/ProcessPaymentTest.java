package com.gsa.ecommerce.payment.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.gsa.ecommerce.payment.infra.factories.FakePaymentProcessFactory;
import com.gsa.ecommerce.payment.infra.gateways.paymentprocess.FakePaymentProcessGateway;

public class ProcessPaymentTest {
  private ProcessPayment processPayment;

  private FakePaymentProcessGateway fakePaymentProcessGateway;
  private FakePaymentProcessFactory fakePaymentProcessFactory;

  @BeforeEach
  public void beforeEach() {
    this.fakePaymentProcessGateway = new FakePaymentProcessGateway();
    this.fakePaymentProcessFactory = new FakePaymentProcessFactory();

    this.processPayment = new ProcessPayment(this.fakePaymentProcessGateway, this.fakePaymentProcessFactory);
  }

  @Test
  public void it_should_process_payment() {
    ProcessPayment.Input input = new ProcessPayment.Input(UUID.randomUUID(), ProcessPayment.Options.PIX);

    this.processPayment.execute(input);

    assertEquals(1, this.fakePaymentProcessGateway.payments().size());
  }

}
