package com.sidit.pricecalculator.domain.model;

import java.util.Objects;
import java.util.UUID;

public record Product(UUID id, String name, UnitPrice unitPrice) {

  public Product {
    Objects.requireNonNull(id);
    Objects.requireNonNull(name);
    Objects.requireNonNull(unitPrice);
  }
}
