package com.gsa.ecommerce.shoppingcart.domain.models;

import java.math.BigDecimal;

import com.gsa.ecommerce.core.infra.exceptions.ValidationException;

public final class Money {
  private final BigDecimal amount;

  private Money(BigDecimal amount) {
    this.amount = amount;
  }

  public static Money create(BigDecimal amount) throws ValidationException {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new ValidationException("Amount cannot be negative");
    }

    return new Money(amount);
  }

  public static Money reconstitute(BigDecimal amount) {
    return new Money(amount);
  }

  public BigDecimal amount() {
    return amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Money other = (Money) obj;
    if (amount == null) {
      if (other.amount != null)
        return false;
    } else if (!amount.equals(other.amount))
      return false;
    return true;
  }

}
