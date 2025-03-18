package com.currency_service.service;

import com.currency_service.dto.ExchangeRateDTO;
import com.currency_service.error.CustomException;
import com.currency_service.response.CurrencyResponse;
import com.currency_service.utils.Constants;
import com.currency_service.utils.CurrencyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CurrencyUtils currencyUtils;

    public CurrencyService(CurrencyUtils currencyUtils) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.currencyUtils = currencyUtils;
    }

    public CurrencyResponse getExchangeRate(String baseCurrency, String targetCurrency, double amount) {

        currencyUtils.validateCurrency(baseCurrency);
        currencyUtils.validateCurrency(targetCurrency);

        // Construct the API URL with the base currency
        String apiUrl = "https://v6.exchangerate-api.com/v6/7366f8e7412617dbeeeeb3b9/latest/" + baseCurrency;

        // Perform a GET request to the specified URL and store the response
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Check if the HTTP response code indicates a successful request
        if (response.getStatusCode().is2xxSuccessful()) {

            // Deserialize the JSON response using Jackson into an ExchangeRateDTO object
            ExchangeRateDTO exchangeRateDTO = null;

            try {

                exchangeRateDTO = objectMapper.readValue(response.getBody(), ExchangeRateDTO.class);

            } catch (JsonProcessingException e) {
                throw new CustomException(
                        Constants.MESSAGE_JSON_PROCESSING,
                        HttpStatus.BAD_REQUEST.value(),
                        Constants.ERROR_JSON_PROCESSING
                );
            }

            // Extract the exchange rate for the target currency
            double exchangeRate = exchangeRateDTO.getConversionRates().get(targetCurrency);

            // Calculate the converted total amount based on the exchange rate
            double totalConverted = exchangeRate * amount;

            // Return an CurrencyResponse containing requested data
            return new CurrencyResponse(baseCurrency, targetCurrency, BigDecimal.valueOf(exchangeRate), BigDecimal.valueOf(totalConverted));

        } else {
            throw new CustomException(
                    Constants.MESSAGE_RETRIEVING_EXCHANGE_RATE,
                    HttpStatus.SERVICE_UNAVAILABLE.value(),
                    Constants.ERROR_RETRIEVING_EXCHANGE_RATE
            );
        }
    }
}
