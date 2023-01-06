package com.sidit.pricecalculator.domain.repositories;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface PercentDiscountConfigRepository {
  Optional<BigDecimal> findByProductId(UUID productId);

  BigDecimal getDefault();
}
