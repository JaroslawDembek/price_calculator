package com.sidit.pricecalculator.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record MonetaryAmount(BigDecimal amount, String currency) {

  public MonetaryAmount {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("MonetaryAmount should be >= 0.00, actual [%s]".formatted(amount.toString()));
    }
    //validate currency code
    Currency.getInstance(currency);
  }

  public MonetaryAmount multiply(BigDecimal multiplicand) {
    //only positive values allowed
    if (Objects.requireNonNull(multiplicand, "Multiplicand cannot be NULL!").compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException(
          "Multiplicand should be > 0.00, actual [%s]".formatted(multiplicand.toString()));
    }
    //arbitrary two decimal digits (simplification)
    return new MonetaryAmount(
        this.amount.multiply(multiplicand).setScale(2, RoundingMode.HALF_UP)
        , this.currency);
  }
}
