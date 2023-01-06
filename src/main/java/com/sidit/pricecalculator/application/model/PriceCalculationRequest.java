package com.sidit.pricecalculator.application.model;

import java.util.UUID;

public record PriceCalculationRequest(UUID productId, Integer quantity) {

}
