package com.currency_service.error;

import com.currency_service.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    void testCustomException(){

        CustomException customException = new CustomException(
                "CurrencyNotFound",
                HttpStatus.NOT_FOUND.value(),
                Constants.ERROR_CURRENCY_NOT_FOUND
        );

        when(webRequest.getDescription(false)).thenReturn("uri=/exchange-rate");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleCustomException(customException, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        customException = new CustomException(
                Constants.MESSAGE_JSON_PROCESSING,
                HttpStatus.BAD_REQUEST.value(),
                Constants.ERROR_JSON_PROCESSING);

        response = globalExceptionHandler.handleCustomException(customException, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        customException = new CustomException(
                Constants.MESSAGE_RETRIEVING_EXCHANGE_RATE,
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                Constants.ERROR_RETRIEVING_EXCHANGE_RATE);

        response = globalExceptionHandler.handleCustomException(customException, webRequest);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());

        customException = new CustomException(
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR");

        response = globalExceptionHandler.handleCustomException(customException, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void testHandleUrlNotFoundTest() {

        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/exchange-ratee", new HttpHeaders());
        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleUrlNotFound(ex, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}