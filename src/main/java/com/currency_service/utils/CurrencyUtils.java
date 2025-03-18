package com.currency_service.utils;

import com.currency_service.error.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.money.Monetary;
import javax.money.UnknownCurrencyException;

@Component
public class CurrencyUtils {

    /**
     * Validates if the provided currency code corresponds to a valid ISO 4217 currency.
     *
     * @param currencyCode the ISO 4217 currency code to be validated
     * @throws CustomException if the currency code is not recognized or valid
     */
    public void validateCurrency(String currencyCode) {
        try {
            Monetary.getCurrency(currencyCode);
        } catch (UnknownCurrencyException e) {
            throw new CustomException(
                    "Currency '" + currencyCode + "' not found.",
                    HttpStatus.NOT_FOUND.value(),
                    Constants.ERROR_CURRENCY_NOT_FOUND
            );
        }
    }

}
