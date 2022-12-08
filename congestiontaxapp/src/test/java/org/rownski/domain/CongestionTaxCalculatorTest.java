package org.rownski.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CongestionTaxCalculatorTest {

  @BeforeEach
  void setUp() {}

  @Test
  void get_tax_method_should_return_correct_tax() {

    var calculator = new CongestionTaxCalculator();

    var localDateTime = LocalDateTime.of(2022, 11, 8, 6, 15);
    LocalDateTime[] localDateTimes = {localDateTime};
    Assertions.assertEquals(8, calculator.getTax(Vehicle.CAR, localDateTimes));

    var localDateTime1 = LocalDateTime.of(2022, 11, 8, 6, 15);
    var localDateTime2 = LocalDateTime.of(2022, 11, 8, 6, 54);
    var localDateTime3 = LocalDateTime.of(2022, 11, 8, 15, 15);
    var localDateTime4 = LocalDateTime.of(2022, 11, 8, 19, 15);
    LocalDateTime[] localDateTimesMany = {
      localDateTime1, localDateTime2, localDateTime3, localDateTime4
    };
    Assertions.assertEquals(26, calculator.getTax(Vehicle.CAR, localDateTimesMany));
  }
}
