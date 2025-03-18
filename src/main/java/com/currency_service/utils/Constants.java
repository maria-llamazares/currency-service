package com.currency_service.utils;

public class Constants {

    private Constants() {
    }

    public static final String ERROR_CURRENCY_NOT_FOUND = "CURRENCY_NOT_FOUND";
    public static final String ERROR_JSON_PROCESSING = "ERROR_JSON_PROCESSING";
    public static final String ERROR_RETRIEVING_EXCHANGE_RATE = "ERROR_RETRIEVING_EXCHANGE_RATE";
    public static final String ERROR_URL_NOT_FOUND = "ERROR_URL_NOT_FOUND";

    public static final String MESSAGE_JSON_PROCESSING = "Error deserializing exchange rate JSON.";
    public static final String MESSAGE_RETRIEVING_EXCHANGE_RATE = "Error retrieving the exchange rate.";

    public static final int CODE_ERROR_NOT_FOUND = 404;
    public static final int CODE_ERROR_BAD_REQUEST = 400;
    public static final int CODE_ERROR_SERVICE_UNAVAILABLE = 503;
}
