package com.sidit.pricecalculator.domain.services;

import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.TotalPrice;

@FunctionalInterface
public interface DiscountRule {
  TotalPrice apply(DiscountRuleContext ctx);
}
