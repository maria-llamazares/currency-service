package com.currency_service.controller;

import com.currency_service.response.CurrencyResponse;
import com.currency_service.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrencyControllerTest {


    @InjectMocks
    private CurrencyController currencyController;

    @Mock
    private CurrencyService currencyService;

    @Test
    void testGetExchangeRate() {

        String baseCurrency = "EUR";
        String targetCurrency = "USD";
        double amount = 100.0;

        BigDecimal expectedRate = new BigDecimal("1.09");
        BigDecimal expectedTotal = new BigDecimal("109.00");

        CurrencyResponse expectedResponse = new CurrencyResponse(baseCurrency, targetCurrency, expectedRate, expectedTotal);
        when(currencyService.getExchangeRate(baseCurrency, targetCurrency, amount)).thenReturn(expectedResponse);

        CurrencyResponse actualResponse = currencyController.getExchangeRate(baseCurrency, targetCurrency, amount);

        assertEquals(expectedResponse.getBaseCurrency(), actualResponse.getBaseCurrency());
        assertEquals(expectedResponse.getTargetCurrency(), actualResponse.getTargetCurrency());
        assertEquals(expectedResponse.getExchangeRate(), actualResponse.getExchangeRate());
        assertEquals(expectedResponse.getTotalConverted(), actualResponse.getTotalConverted());

    }

}
