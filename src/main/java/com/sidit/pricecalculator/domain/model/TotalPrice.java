package com.sidit.pricecalculator.domain.model;

import java.util.Objects;

public record TotalPrice(MonetaryAmount amount) {

  public TotalPrice {
    Objects.requireNonNull(amount);
  }

}
