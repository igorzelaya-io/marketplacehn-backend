package com.marketplacehn.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface Response<T>{

    T getPayload();
    void setPayload(T payload);

    int getHttpStatus();
    void setHttpStatus(int httpStatus);

    LocalDateTime getTimestamp();
    void setTimestamp(LocalDateTime timestamp);

    String getMessage();
    void setMessage(String message);

    default ResponseEntity<? extends Response<T>> buildResponseEntity
            (final HttpStatus httpStatus, final String message,
             final T payload) {

        setPayload(payload);
        setHttpStatus(httpStatus.value());
        setTimestamp(LocalDateTime.now());
        setMessage(message);
        return new ResponseEntity<>(this, httpStatus);
    }

}
