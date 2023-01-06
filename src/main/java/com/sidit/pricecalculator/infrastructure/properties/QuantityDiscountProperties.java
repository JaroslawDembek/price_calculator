package com.sidit.pricecalculator.infrastructure.properties;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quantity")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class QuantityDiscountProperties {

  /**
   * Applied quantity discount: max value <= ordered quantity
   */
  Map<UUID, SortedMap<Integer, BigDecimal>> perProduct;

  SortedMap<Integer, BigDecimal> perQuantity;

}
