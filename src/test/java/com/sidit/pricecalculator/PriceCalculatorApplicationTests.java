package com.sidit.pricecalculator;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.sidit.pricecalculator.application.model.PriceCalculationResponse;
import com.sidit.pricecalculator.domain.model.MonetaryAmount;
import com.sidit.pricecalculator.domain.model.Product;
import com.sidit.pricecalculator.domain.model.UnitPrice;
import com.sidit.pricecalculator.infrastructure.repositories.ProductRepositoryImpl;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceCalculatorApplicationTests {

  @LocalServerPort
  int port;

  @Autowired
  ProductRepositoryImpl productRepositoryForTests;

  @BeforeEach
  void init() {
    productRepositoryForTests.clear();
    var unitPriceForA = new MonetaryAmount(BigDecimal.valueOf(1000.00), "PLN");
    productRepositoryForTests.add(
        new Product(UUID.fromString("2291c08e-99da-4d58-9a25-908ce039f9fd"), "A", new UnitPrice(unitPriceForA)));

    var unitPriceForB = new MonetaryAmount(BigDecimal.valueOf(1000.00), "EUR");
    productRepositoryForTests.add(
        new Product(UUID.fromString("34423624-b441-4072-ac1a-709cdf25f94f"), "B", new UnitPrice(unitPriceForB)));
  }

  @Test
  void when_getTotalPercentDiscountFor2As_thenOK() {
    var response = given().port(port).log().all()
        .queryParam("productId", "2291c08e-99da-4d58-9a25-908ce039f9fd")
        .queryParam("quantity", "2")
        .queryParam("discountPlan", "TOTAL_PERCENT")
        .when().get("/totalPrice");
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body().as(PriceCalculationResponse.class)).isEqualTo(
        new PriceCalculationResponse("1900.00", "PLN")
    );
  }

  @Test
  void when_getQuantityDiscountFor5Bs_thenOK() {
    var response = given().port(port).log().all()
        .queryParam("productId", "34423624-b441-4072-ac1a-709cdf25f94f")
        .queryParam("quantity", "5")
        .queryParam("discountPlan", "BY_QUANTITY")
        .when().get("/totalPrice");
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body().as(PriceCalculationResponse.class)).isEqualTo(
        new PriceCalculationResponse("4500.00", "EUR")
    );
  }

}
