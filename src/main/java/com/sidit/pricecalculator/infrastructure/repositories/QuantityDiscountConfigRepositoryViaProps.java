package com.sidit.pricecalculator.infrastructure.repositories;

import com.sidit.pricecalculator.domain.repositories.QuantityDiscountConfigRepository;
import com.sidit.pricecalculator.infrastructure.properties.QuantityDiscountProperties;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.SortedMap;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(QuantityDiscountProperties.class)
@RequiredArgsConstructor
public class QuantityDiscountConfigRepositoryViaProps implements QuantityDiscountConfigRepository {

  private final QuantityDiscountProperties properties;

  @Override
  public Optional<BigDecimal> findByProductIdAndQuantity(@NonNull UUID productId, @NonNull Integer quantity) {
    var perProduct = properties.getPerProduct().get(productId);
    if (perProduct == null) {
      return Optional.empty();
    }
    return findPerQuantity(perProduct, quantity);
  }

  @Override
  public Optional<BigDecimal> getDefault(@NonNull Integer quantity) {
    return findPerQuantity(properties.getPerQuantity(), quantity);
  }

  private Optional<BigDecimal> findPerQuantity(SortedMap<Integer, BigDecimal> quantityBands, int quantity) {
    var discountKey = quantityBands.keySet().stream().mapToInt(x -> x)
        .takeWhile(k -> k <= quantity)
        .max();
    if (discountKey.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(quantityBands.get(discountKey.getAsInt()));
    }
  }
}
