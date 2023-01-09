package com.sidit.pricecalculator.domain.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.MonetaryAmount;
import com.sidit.pricecalculator.domain.model.Product;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.infrastructure.repositories.ProductRepositoryImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoDiscountDiscountRuleTest {

  static final UUID prodAId = UUID.fromString("2291c08e-99da-4d58-9a25-908ce039f9fd");

  ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
  NoDiscountDiscountRule rule = new NoDiscountDiscountRule(productRepository);

  @BeforeEach
  void reset() {
    productRepository.clear();
  }

  @Test
  void when_100As_then_noDiscount() {
    //given
    var unitPriceForA = new MonetaryAmount(BigDecimal.valueOf(1000.00), "PLN");
    productRepository.add(
        new Product(prodAId, "A", new UnitPrice(unitPriceForA)));
    //then
    var result = rule.apply(new DiscountRuleContext(prodAId, 100));
    assertThat(result.amount()).isEqualTo(
        new MonetaryAmount(BigDecimal.valueOf(100_000).setScale(2, RoundingMode.HALF_UP), "PLN"));
  }

  @Test
  void when_noProdInRepo_then_exception() {
    assertThatThrownBy(() -> {
      rule.apply(new DiscountRuleContext(prodAId, 1));
    }).isInstanceOf(RuntimeException.class).hasNoCause();
  }


}
