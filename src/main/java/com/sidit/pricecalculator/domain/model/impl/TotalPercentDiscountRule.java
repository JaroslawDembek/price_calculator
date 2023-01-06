package com.sidit.pricecalculator.domain.model.impl;

import com.sidit.pricecalculator.domain.model.DiscountRule;
import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.TotalPrice;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.domain.repositories.PercentDiscountConfigRepository;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.UUID;

public class TotalPercentDiscountRule implements DiscountRule {

  private final ProductRepository productRepository;
  private final PercentDiscountConfigRepository percentDiscountConfigRepository;

  public TotalPercentDiscountRule(ProductRepository productRepository,
      PercentDiscountConfigRepository percentDiscountConfigRepository) {
    this.productRepository = productRepository;
    this.percentDiscountConfigRepository = percentDiscountConfigRepository;
  }

  private UnitPrice getUnitPrice(UUID productId) {
    //TODO some dedicated exception for no product found needed
    return productRepository.findById(productId).orElseThrow(RuntimeException::new).unitPrice();
  }

  public TotalPrice apply(DiscountRuleContext ctx) {
    var unitPrice = getUnitPrice(ctx.productId()).amount();
    var totalAmount = unitPrice.multiply(BigDecimal.valueOf(ctx.quantity()));
    return new TotalPrice(totalAmount.multiply(getPercentDiscount(ctx)));
  }

  private BigDecimal getPercentDiscount(DiscountRuleContext ctx) {
    //Could be determined on any ctx value
    //Simplification: value per product
    return percentDiscountConfigRepository.findByProductId(ctx.productId())
        .orElseGet(percentDiscountConfigRepository::getDefault);
  }
}
