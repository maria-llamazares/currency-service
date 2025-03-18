package com.currency_service.service;

import com.currency_service.dto.ExchangeRateDTO;
import com.currency_service.error.CustomException;
import com.currency_service.response.CurrencyResponse;
import com.currency_service.utils.Constants;
import com.currency_service.utils.CurrencyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CurrencyUtils currencyUtils;

    @Test
    void testGetExchangeRate_OK() {

        String baseCurrency = "EUR";
        String targetCurrency = "USD";
        double amount = 100.0;

        doNothing().when(currencyUtils).validateCurrency("EUR");
        doNothing().when(currencyUtils).validateCurrency("USD");

        String apiUrl = "https://v6.exchangerate-api.com/v6/7366f8e7412617dbeeeeb3b9/latest/" + baseCurrency;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
        when(restTemplate.getForEntity(apiUrl, String.class)).thenReturn(responseEntity);

        CurrencyResponse result = currencyService.getExchangeRate(baseCurrency, targetCurrency, amount);

        assertEquals(baseCurrency, result.getBaseCurrency());
        assertEquals(targetCurrency, result.getTargetCurrency());

    }


    void testGetExchangeRate_NOK() {

        String baseCurrency = "EUR";
        String targetCurrency = "USD";
        double amount = 100.0;

        String apiUrl = "https://v6.exchangerate-api.com/v6/7366f8e7412617dbeeeeb3b9/latest/" + baseCurrency;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(apiUrl, String.class)).thenReturn(responseEntity);

        System.out.println(responseEntity.getStatusCode().value());
        System.out.println(responseEntity.getStatusCode().is2xxSuccessful());

        currencyService.getExchangeRate(baseCurrency, targetCurrency, amount);

    }

    void testGetExchangeRate_JsonError() throws JsonProcessingException {

        String baseCurrency = "EUR";
        String targetCurrency = "USD";
        double amount = 100.0;

        String apiUrl = "https://v6.exchangerate-api.com/v6/7366f8e7412617dbeeeeb3b9/latest/" + baseCurrency;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{invalidJson}", HttpStatus.OK);
        when(restTemplate.getForEntity(apiUrl, String.class)).thenReturn(responseEntity);

        when(objectMapper.readValue(anyString(), eq(ExchangeRateDTO.class)))
                .thenThrow(JsonProcessingException.class);

        CustomException exception = assertThrows(CustomException.class,
                () -> currencyService.getExchangeRate(baseCurrency, targetCurrency, amount)
        );

        assertEquals(Constants.MESSAGE_JSON_PROCESSING, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
        assertEquals(Constants.ERROR_JSON_PROCESSING, exception.getErrorCode());

    }
}
