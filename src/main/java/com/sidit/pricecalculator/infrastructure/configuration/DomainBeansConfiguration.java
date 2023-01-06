package com.sidit.pricecalculator.infrastructure.configuration;

import com.sidit.pricecalculator.domain.repositories.PercentDiscountConfigRepository;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import com.sidit.pricecalculator.domain.repositories.QuantityDiscountConfigRepository;
import com.sidit.pricecalculator.domain.services.DiscountTotalPriceCalculator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({

})
public class DomainBeansConfiguration {

  @Bean
  DiscountTotalPriceCalculator discountTotalPriceCalculator(ProductRepository productRepository,
      PercentDiscountConfigRepository percentDiscountConfigRepository,
      QuantityDiscountConfigRepository quantityDiscountConfigRepository) {
    return new DiscountTotalPriceCalculator(productRepository, percentDiscountConfigRepository,
        quantityDiscountConfigRepository);
  }
}
