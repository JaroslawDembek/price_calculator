package com.sidit.pricecalculator.domain.model;

@FunctionalInterface
public interface DiscountRule {
  TotalPrice apply(DiscountRuleContext ctx);
}
