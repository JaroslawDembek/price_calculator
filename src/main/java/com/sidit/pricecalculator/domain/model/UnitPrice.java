package com.sidit.pricecalculator.domain.model;

import java.util.Objects;

public record UnitPrice(MonetaryAmount amount) {

  public UnitPrice {
    Objects.requireNonNull(amount);
  }

}
