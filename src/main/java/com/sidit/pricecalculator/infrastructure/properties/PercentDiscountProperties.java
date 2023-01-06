package com.sidit.pricecalculator.infrastructure.properties;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "percent")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class PercentDiscountProperties {

  Map<UUID, BigDecimal> productDiscount;

  BigDecimal defaultValue;

}
