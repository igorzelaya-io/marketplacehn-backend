package com.marketplacehn.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonSerialize
@NoArgsConstructor
public class BaseResponse<T> implements Response<T>{

    @JsonProperty
    private T payload;

    @JsonProperty
    private int httpStatus;

    @JsonProperty
    private String message;

    @JsonProperty
    private LocalDateTime timestamp;

    @Override
    public T getPayload() {
        return payload;
    }

    @Override
    public void setPayload(final T payload) {
        this.payload = payload;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void setHttpStatus(final int statusCode) {
        this.httpStatus = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
