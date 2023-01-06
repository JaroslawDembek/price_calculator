package com.sidit.pricecalculator.application.controllers;

import com.sidit.pricecalculator.application.mappers.PriceCalculationResponseMapper;
import com.sidit.pricecalculator.application.model.PriceCalculationResponse;
import com.sidit.pricecalculator.application.model.RequestedDiscount;
import com.sidit.pricecalculator.domain.model.DiscountRuleContext;
import com.sidit.pricecalculator.domain.services.DiscountTotalPriceCalculator;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountedTotalPriceController {

  private final DiscountTotalPriceCalculator discountTotalPriceCalculator;
  private final PriceCalculationResponseMapper priceCalculationResponseMapper;

  public DiscountedTotalPriceController(DiscountTotalPriceCalculator discountTotalPriceCalculator,
      PriceCalculationResponseMapper priceCalculationResponseMapper) {
    this.discountTotalPriceCalculator = discountTotalPriceCalculator;
    this.priceCalculationResponseMapper = priceCalculationResponseMapper;
  }

  @GetMapping("/totalPrice")
  PriceCalculationResponse getDiscountedTotalPrice(
      @RequestParam("productId") UUID productId,
      @RequestParam("quantity") int quantity,
      @RequestParam("discountPlan") Optional<RequestedDiscount> discountPlanOptional) {

    var discountPlan = discountPlanOptional.orElse(RequestedDiscount.DEFAULT);
    var ctx = new DiscountRuleContext(productId, quantity);
    return priceCalculationResponseMapper.map(
        discountTotalPriceCalculator.calculateDiscountedTotalPrice(discountPlan.name(), ctx));
  }
}
