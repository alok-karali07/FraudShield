package com.example.service.exception;

import com.example.transaction.rest.model.RestErrorCode;
import com.example.transaction.rest.model.RestErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorFactory;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RestErrorResponse> handleBusinessException(BusinessException ex) {
        logBusinessError(ex);
        RestErrorResponse restErrorResponse = errorFactory.buildRestErrorResponse(ex.getStatus(), ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(restErrorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.info("Resource not found: {}", ex.getMessage());
        RestErrorResponse restErrorResponse = errorFactory.buildRestErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String details = extractValidationDetails(ex);
        log.warn("Validation failed: {}", details);
        var body = errorFactory.buildRestErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.VALIDATION_FAILED, "Validation failed: " + details);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage(), ex);

        RestErrorResponse response = new RestErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                RestErrorCode.SYSTEM_ERROR,
                ex.getMessage(),
                Instant.now().toEpochMilli()
        );
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled system exception: ", ex);

        RestErrorResponse response = new RestErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                RestErrorCode.SYSTEM_ERROR,
                "Internal server error.",
                Instant.now().toEpochMilli()
        );
        return ResponseEntity.internalServerError().body(response);
    }

    private String extractValidationDetails(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

    private void logBusinessError(BusinessException ex) {
        if (ex.getStatus() >= 500) {
            log.error("Critical business error: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        } else {
            log.warn("Business validation: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        }
    }
}