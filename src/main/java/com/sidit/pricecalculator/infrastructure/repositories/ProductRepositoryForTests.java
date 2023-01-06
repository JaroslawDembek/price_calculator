package com.sidit.pricecalculator.infrastructure.repositories;

import com.sidit.pricecalculator.domain.model.Product;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProductRepositoryForTests implements ProductRepository {

  @Override
  public Optional<Product> findById(UUID id) {
    return Optional.empty();
  }
}
