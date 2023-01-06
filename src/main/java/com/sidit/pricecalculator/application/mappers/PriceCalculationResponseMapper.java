package com.sidit.pricecalculator.application.mappers;

import com.sidit.pricecalculator.application.model.PriceCalculationResponse;
import com.sidit.pricecalculator.domain.model.TotalPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceCalculationResponseMapper {

  @Mapping(source = "price.amount.amount", target = "amount")
  @Mapping(source = "price.amount.currency", target = "currency")
  PriceCalculationResponse map(TotalPrice price);
}
