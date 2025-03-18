package com.currency_service.controller;

import com.currency_service.error.CustomErrorResponse;
import com.currency_service.response.CurrencyResponse;
import com.currency_service.service.CurrencyService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @Info(
                title = "Currency Exchange API",
                version = "1.0",
                description = "The Currency Exchange API allows users to convert between different currencies by retrieving real-time exchange rates and calculating the equivalent amount in the target currency. This API provides essential functionality for currency conversion, making it a valuable tool for individuals and businesses dealing with international transactions, travel, or financial management.",
                contact = @Contact(name = "Maria Llamazares", email = "mariallamazareslopez@gmail.com")
        )
)

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get exchange rate and converted amount.", description = "Retrieves the current exchange rate between the base currency and target currency, and calculates the total converted amount based on the provided amount.")
    @GetMapping("/exchange-rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchange rate and total converted amount retrieved successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CurrencyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error deserializing exchange rate JSON.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Error Deserializing Exchange Rate JSON", value = """
                        {
                            "dateTimeStamp": "2025-03-18 10:17:57",
                            "status": 400,
                            "errorCode": "ERROR_JSON_PROCESSING",
                            "message": "Error deserializing exchange rate JSON.",
                            "path": "/exchange-rate"
                        }""")
                            })),
            @ApiResponse(responseCode = "404", description = "Currency not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Currency Not Found", value = """
                        {
                            "dateTimeStamp": "2025-03-18 09:39:50",
                            "status": 404,
                            "errorCode": "ERROR_CURRENCY_NOT_FOUND",
                            "message": "Currency 'XYZ' not found.",
                            "path": "/exchange-rate"
                        }""")
                            })),
            @ApiResponse(responseCode = "503", description = "Error retrieving the exchange rate.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "Exchange Rate Retrieval Error", value = """
                        {
                            "dateTimeStamp": "2025-03-18 10:52:45",
                            "status": 503,
                            "errorCode": "ERROR_RETRIEVING_EXCHANGE_RATE",
                            "message": "Error retrieving the exchange rate.",
                            "path": "/exchange-rate"
                        }""")
                            }))
    })
    public CurrencyResponse getExchangeRate( @Schema(description = "The base currency for the exchange rate.", example = "EUR")
                                            @RequestParam String baseCurrency,
                                            @Schema(description = "The target currency for the exchange rate.",example = "USD")
                                            @RequestParam String targetCurrency,
                                            @Schema(description = "The amount to be converted.",example = "100")
                                            @RequestParam double amount) {
        return currencyService.getExchangeRate(baseCurrency, targetCurrency, amount);
    }
}