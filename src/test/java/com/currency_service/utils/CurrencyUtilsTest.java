package com.currency_service.utils;

import com.currency_service.error.CustomException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyUtilsTest {

    @InjectMocks
    private CurrencyUtils currencyUtils;

    @Test
    void testValidateCurrency_validCurrency() {

        String currency = "EUR";
        assertDoesNotThrow(() -> currencyUtils.validateCurrency(currency));

    }

    @Test
    void testValidateCurrency_invalidCurrency() {

        String currency = "X";

        CustomException exception = assertThrows(CustomException.class,
                () -> currencyUtils.validateCurrency(currency)
        );

        assertEquals("Currency '" + currency + "' not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatus());
        assertEquals(Constants.ERROR_CURRENCY_NOT_FOUND, exception.getErrorCode());

    }

}
