package com.currency_service.error;

import com.currency_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type {@link CustomException}.
     * Constructs a {@link CustomErrorResponse} based on the exception details
     * and the HTTP status determined by the exception's status.
     *
     * @param ex the exception of type {@link CustomException} that has occurred
     * @param request the {@link WebRequest} containing request details
     * @return a {@link ResponseEntity} containing the {@link CustomErrorResponse}
     *         and the corresponding HTTP status
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException ex, WebRequest request) {

        HttpStatus status = switch (ex.getStatus()) {
            case Constants.CODE_ERROR_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case Constants.CODE_ERROR_BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case Constants.CODE_ERROR_SERVICE_UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                status.value(),
                ex.getErrorCode(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Handles the {@link NoHandlerFoundException} triggered when a requested URL is not found.
     * Constructs a {@link CustomErrorResponse} containing details such as the timestamp,
     * HTTP status, error code, exception message, and request path.
     *
     * @param ex the {@link NoHandlerFoundException} that was thrown when the URL could not be found
     * @param request the {@link WebRequest} containing details of the originating HTTP request
     * @return a {@link ResponseEntity} containing the {@link CustomErrorResponse}
     *         with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUrlNotFound(NoHandlerFoundException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                Constants.ERROR_URL_NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
