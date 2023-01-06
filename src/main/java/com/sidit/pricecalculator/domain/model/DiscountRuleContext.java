package com.sidit.pricecalculator.domain.model;

import java.util.Objects;
import java.util.UUID;

//Simplification: quantity could be in g, kg etc.
public record DiscountRuleContext(UUID productId, Integer quantity) {

  public DiscountRuleContext {
    Objects.requireNonNull(productId, "Product cannot be NULL!");
    if (Objects.requireNonNull(quantity,"Quantity cannot be NULL!") < 1) {
      throw new IllegalArgumentException("Quantity should be > 0, actual [%d]".formatted(quantity));
    }
  }
}
