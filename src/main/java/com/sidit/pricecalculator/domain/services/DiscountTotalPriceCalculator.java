package com.sidit.pricecalculator.domain.services;

import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.TotalPrice;
import com.sidit.pricecalculator.domain.services.impl.NoDiscountDiscountRule;
import com.sidit.pricecalculator.domain.services.impl.QuantityDiscountRule;
import com.sidit.pricecalculator.domain.services.impl.TotalPercentDiscountRule;
import com.sidit.pricecalculator.domain.repositories.PercentDiscountConfigRepository;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import com.sidit.pricecalculator.domain.repositories.QuantityDiscountConfigRepository;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DiscountTotalPriceCalculator {

  public static final String DEFAULT_RULE_NAME = "DEFAULT";
  private static final ConcurrentMap<String, DiscountRule> discountRules = new ConcurrentHashMap<>();

  private final ProductRepository productRepository;
  private final PercentDiscountConfigRepository percentDiscountConfigRepository;
  private final QuantityDiscountConfigRepository quantityDiscountConfigRepository;

  public DiscountTotalPriceCalculator(ProductRepository productRepository,
      PercentDiscountConfigRepository percentDiscountConfigRepository,
      QuantityDiscountConfigRepository quantityDiscountConfigRepository) {
    this.productRepository = productRepository;
    this.percentDiscountConfigRepository = percentDiscountConfigRepository;
    this.quantityDiscountConfigRepository = quantityDiscountConfigRepository;
    initRules();
  }

  public void registerDiscountRule(String name, DiscountRule rule) {
    discountRules.put(name, rule);
  }

  public void initRules() {
    this.registerDiscountRule(DEFAULT_RULE_NAME, new NoDiscountDiscountRule(productRepository));
    this.registerDiscountRule("TOTAL_PERCENT",
        new TotalPercentDiscountRule(productRepository, percentDiscountConfigRepository));
    this.registerDiscountRule("BY_QUANTITY",
        new QuantityDiscountRule(productRepository, quantityDiscountConfigRepository));
  }

  public TotalPrice calculateDiscountedTotalPrice(String discountRule, DiscountRuleContext ctx) {
    Objects.requireNonNull(discountRule, "Discount rule cannot be NULL!");
    return Objects.requireNonNull(discountRules.get(discountRule), "Rule [%s] not found".formatted(discountRule))
        .apply(ctx);
  }
}
