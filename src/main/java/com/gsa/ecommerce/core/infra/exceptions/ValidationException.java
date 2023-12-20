package com.gsa.ecommerce.core.infra.exceptions;

public class ValidationException extends Exception {
  public ValidationException(String message) {
    super(message);
  }
}
