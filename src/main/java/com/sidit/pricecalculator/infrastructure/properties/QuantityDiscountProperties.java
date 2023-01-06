package com.sidit.pricecalculator.infrastructure.properties;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "quantity")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Validated
public class QuantityDiscountProperties {

  /**
   * Applied quantity discount: max value <= ordered quantity
   */
  @Valid
  Map<UUID,
      SortedMap<@NotNull Integer,
                @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "1.0", inclusive = false) BigDecimal>
      > perProduct;

  @Valid
  SortedMap<@NotNull Integer,
      @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "1.0", inclusive = false) BigDecimal
      > perQuantity;

}
