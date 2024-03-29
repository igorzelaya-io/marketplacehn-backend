package com.marketplacehn.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize
@NoArgsConstructor
public class BaseResponse<T> implements Response<T> {

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
    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
