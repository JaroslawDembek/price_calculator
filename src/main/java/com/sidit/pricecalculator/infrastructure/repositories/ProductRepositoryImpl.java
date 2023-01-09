package com.sidit.pricecalculator.infrastructure.repositories;

import com.sidit.pricecalculator.domain.model.Product;
import com.sidit.pricecalculator.domain.repositories.ProductRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.NonNull;

public class ProductRepositoryImpl implements ProductRepository {

  private static final ConcurrentMap<UUID, Product> products = new ConcurrentHashMap<>();

  @Override
  public Optional<Product> findById(@NonNull UUID id) {
    return Optional.ofNullable(products.get(id));
  }

  public Product add(@NonNull Product product) {
    return products.put(product.id(), product);
  }

  public void clear() {
    products.clear();
  }
}
