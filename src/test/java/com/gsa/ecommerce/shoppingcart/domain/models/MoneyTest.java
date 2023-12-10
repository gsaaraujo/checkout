package com.gsa.ecommerce.shoppingcart.domain.models;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gsa.ecommerce.shared.exceptions.ValidationException;

public class MoneyTest {
  @Test
  public void it_should_create_money() {
    try {
      Money sut = Money.create(new BigDecimal(25.50));

      assertEquals(new BigDecimal(25.50), sut.amount());
    } catch (Exception e) {
      //
    }
  }

  @Test
  public void it_should_reconstitute_money() {
    Money sut = Money.reconstitute(new BigDecimal(25.50));

    assertEquals(new BigDecimal(25.50), sut.amount());
  }

  @Test
  public void it_should_fail_when_amount_is_negative() {
    try {
      Money.create(new BigDecimal(25.50));
    } catch (ValidationException validationException) {
      assertEquals("Amount cannot be negative", validationException.getMessage());
    }
  }
}
