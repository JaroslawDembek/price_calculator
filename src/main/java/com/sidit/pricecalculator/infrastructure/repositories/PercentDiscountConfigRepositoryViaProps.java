package com.sidit.pricecalculator.infrastructure.repositories;

import com.sidit.pricecalculator.domain.repositories.PercentDiscountConfigRepository;
import com.sidit.pricecalculator.infrastructure.properties.PercentDiscountProperties;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(PercentDiscountProperties.class)
@RequiredArgsConstructor
public class PercentDiscountConfigRepositoryViaProps implements PercentDiscountConfigRepository {

  private final PercentDiscountProperties properties;

  @Override
  public Optional<BigDecimal> findByProductId(@NonNull UUID productId) {
    return Optional.ofNullable(properties.getProductDiscount().get(productId));
  }

  @Override
  public BigDecimal getDefault() {
    return properties.getDefaultValue();
  }
}
