package com.sidit.pricecalculator.domain.repositories;

import com.sidit.pricecalculator.domain.model.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
  Optional<Product> findById(UUID id);
}
