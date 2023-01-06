package com.sidit.pricecalculator.infrastructure.properties;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "percent")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Validated
public class PercentDiscountProperties {

  @Valid
  Map<@NotNull UUID,
      @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "1.0", inclusive = false) BigDecimal
      > productDiscount;

  @NotNull
  @DecimalMin(value = "0.0")
  @DecimalMax(value = "1.0", inclusive = false)
  BigDecimal defaultValue;

}
