package com.sidit.pricecalculator.domain.repositories;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface QuantityDiscountConfigRepository {

  Optional<BigDecimal> findByProductIdAndQuantity(UUID productId, Integer quantity);

  Optional<BigDecimal> getDefault(Integer quantity);

}
