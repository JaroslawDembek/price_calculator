package com.sidit.pricecalculator.domain.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.MonetaryAmount;
import com.sidit.pricecalculator.domain.model.Product;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.domain.repositories.PercentDiscountConfigRepository;
import com.sidit.pricecalculator.infrastructure.repositories.ProductRepositoryForTests;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalPercentDiscountRuleTest {

  static final String PROD_A_ID = "2291c08e-99da-4d58-9a25-908ce039f9fd";
  static final String PROD_B_ID = "34423624-b441-4072-ac1a-709cdf25f94f";

  static final PercentDiscountConfigRepository PERCENT_DISCOUNT_CONFIG_REPOSITORY = new PercentDiscountConfigRepository() {
    @Override
    public Optional<BigDecimal> findByProductId(UUID productId) {
      return switch (productId.toString()) {
        case PROD_A_ID -> Optional.of(BigDecimal.valueOf(0.2));
        case PROD_B_ID -> Optional.of(BigDecimal.valueOf(0.1));
        default -> Optional.empty();
      };
    }

    @Override
    public BigDecimal getDefault() {
      return BigDecimal.valueOf(0.1);
    }
  };

  ProductRepositoryForTests productRepository = new ProductRepositoryForTests();

  @BeforeEach
  void reset() {
    productRepository.clear();
  }

  @Test
  void when_whateverQuantity_then_percentIsTheSame() {
    //given
    var unitPriceForA = new MonetaryAmount(BigDecimal.valueOf(1000.00), "PLN");
    productRepository.add(
        new Product(UUID.fromString(PROD_A_ID), "A", new UnitPrice(unitPriceForA)));
    var rule = new TotalPercentDiscountRule(productRepository, PERCENT_DISCOUNT_CONFIG_REPOSITORY);
    //then
    var discountFor300 = rule.getPercentDiscount(new DiscountRuleContext(UUID.fromString(PROD_A_ID), 300));
    var discountFor1 = rule.getPercentDiscount(new DiscountRuleContext(UUID.fromString(PROD_A_ID), 1));
    assertThat(discountFor300).isEqualTo(discountFor1);
  }

  @Test
  void when_noProdInRepo_then_exception() {
    var rule = new TotalPercentDiscountRule(productRepository, PERCENT_DISCOUNT_CONFIG_REPOSITORY);
    assertThatThrownBy(() -> {
      rule.apply(new DiscountRuleContext(UUID.fromString(PROD_A_ID), 1));
    }).isInstanceOf(RuntimeException.class).hasNoCause();
  }
}
