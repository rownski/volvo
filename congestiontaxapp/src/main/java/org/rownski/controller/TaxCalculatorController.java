package org.rownski.controller;

import lombok.RequiredArgsConstructor;
import org.rownski.domain.CongestionTaxCalculator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tax")
@RestController
@RequiredArgsConstructor
public class TaxCalculatorController {

  private final CongestionTaxCalculator congestionTaxCalculator;

  @PostMapping
  TaxResponse calculateTax(@RequestBody TaxRequest request) {
    return TaxResponse.builder()
        .tax(congestionTaxCalculator.getTax(request.vehicle(), request.entrances()))
        .build();
  }

  @PostMapping("/config")
  void configureTolls(@RequestBody TaxRequest request) {

    return;
  }
}
