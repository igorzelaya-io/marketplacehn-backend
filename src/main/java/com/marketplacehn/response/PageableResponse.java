package com.marketplacehn.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize
@NoArgsConstructor
public class PageableResponse<T> implements IPageResponse<T>, Response<T>{

    @JsonProperty
    private List<T> payloadPage;

    @JsonProperty
    private int pageSize;

    @JsonProperty
    private int pageNumber;

    @JsonProperty
    private int numberOfElements;

    @JsonProperty
    private int totalPages;

    @JsonProperty
    private int httpStatus;

    @JsonProperty
    private LocalDateTime timestamp;

    @JsonProperty
    private String message;


    @Override
    public List<T> getPayloadPage() {
        return payloadPage;
    }

    @Override
    public void setPayloadPage(List<T> payloadPage) {
        this.payloadPage = payloadPage;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfElements;
    }

    @Override
    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public T getPayload() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPayload(T payload) {
        throw new UnsupportedOperationException();
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

    public ResponseEntity<? extends Response<T>> buildResponseEntity
            (final HttpStatus httpStatus, final String message,
             final List<T> payloadPage){
        setPayloadPage(payloadPage);
        setHttpStatus(httpStatus.value());
        setTimestamp(LocalDateTime.now());
        setMessage(message);
        return new ResponseEntity<>(this, httpStatus);
    }
}
