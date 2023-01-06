package com.sidit.pricecalculator.domain.model.impl;

import com.sidit.pricecalculator.domain.model.DiscountRule;
import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.model.TotalPrice;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import com.sidit.pricecalculator.domain.repositories.QuantityDiscountConfigRepository;
import java.math.BigDecimal;
import java.util.UUID;

//I assume that discount is in percent. But could be in free units.
public class QuantityDiscountRule implements DiscountRule {

  private final ProductRepository productRepository;
  private final QuantityDiscountConfigRepository quantityDiscountConfigRepository;

  public QuantityDiscountRule(ProductRepository productRepository,
      QuantityDiscountConfigRepository quantityDiscountConfigRepository) {
    this.productRepository = productRepository;
    this.quantityDiscountConfigRepository = quantityDiscountConfigRepository;
  }

  private UnitPrice getUnitPrice(UUID productId) {
    //TODO some dedicated exception for no product found needed
    return productRepository.findById(productId).orElseThrow(RuntimeException::new).unitPrice();
  }

  public TotalPrice apply(DiscountRuleContext ctx) {
    var unitPrice = getUnitPrice(ctx.productId()).amount();
    var totalAmount = unitPrice.multiply(BigDecimal.valueOf(ctx.quantity()));
    return new TotalPrice(totalAmount.multiply(BigDecimal.ONE.subtract(getPercentDiscount(ctx))));
  }

  private BigDecimal getPercentDiscount(DiscountRuleContext ctx) {
    //Could be determined on any ctx value
    //Simplification: value per product and quantity or per quantity
    return quantityDiscountConfigRepository.findByProductIdAndQuantity(ctx.productId(), ctx.quantity())
        .or(() -> quantityDiscountConfigRepository.getDefault(ctx.quantity()))
        //No discount if not specified
        .orElse(BigDecimal.ZERO);
  }
}
