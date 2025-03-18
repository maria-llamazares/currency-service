package com.currency_service.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {

    @Schema(description = "The base currency for the exchange.",
            example = "EUR")
    private String baseCurrency;

    @Schema(description = "The target currency for the exchange.",
            example = "USD")
    private String targetCurrency;

    @Schema(description = "The exchange rate between the base and target currencies.",
            example = "1.0911")
    private BigDecimal exchangeRate;

    @Schema(description = "The total amount in target currency after conversion.",
            example = "109.11")
    private BigDecimal totalConverted;

}
