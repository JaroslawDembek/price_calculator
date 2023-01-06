package com.sidit.pricecalculator.domain.model.impl;

import com.sidit.pricecalculator.domain.model.DiscountRule;
import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.TotalPrice;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.UUID;

public class NoDiscountDiscountRule implements DiscountRule {

  private final ProductRepository productRepository;

  public NoDiscountDiscountRule(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  private UnitPrice getUnitPrice(UUID productId) {
    //TODO some dedicated exception for no product found needed
    return productRepository.findById(productId).orElseThrow(RuntimeException::new).unitPrice();
  }

  public TotalPrice apply(DiscountRuleContext ctx) {
      var unitPrice = getUnitPrice(ctx.productId()).amount();
      return new TotalPrice(unitPrice.multiply(BigDecimal.valueOf(ctx.quantity())));
  }
}
