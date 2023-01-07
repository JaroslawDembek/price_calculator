package com.sidit.pricecalculator.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class MonetaryAmountTest {

  @Test
  void when_amountBelowZero_then_exception() {
    final var MINUS_ONE = BigDecimal.valueOf(-1.0);
    assertThatThrownBy(() -> new MonetaryAmount(MINUS_ONE, "PLN"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("MonetaryAmount should be >= 0.00, actual [%s]", MINUS_ONE.toString());
  }

  @Test
  void when_nonISOCurrency_then_exception() {
    assertThatThrownBy(() -> new MonetaryAmount(BigDecimal.ONE, "AAA"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasNoCause();
  }

  @Test
  void when_multiply_then_roundingApplied() {
    var result = new MonetaryAmount(BigDecimal.TEN, "PLN").multiply(BigDecimal.valueOf(0.3337));
    assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(3.34));
  }

}
