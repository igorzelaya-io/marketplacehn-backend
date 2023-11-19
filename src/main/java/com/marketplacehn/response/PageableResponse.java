package com.marketplacehn.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize
public class PageableResponse<T>  implements IPageResponse<T> {
    @JsonProperty
    private Response<List<T>> response;
    @JsonProperty
    private int pageSize;
    @JsonProperty
    private int pageNumber;
    @JsonProperty
    private int numberOfElements;
    @JsonProperty
    private int totalPages;

    public PageableResponse(Response<List<T>> response, int pageSize, int pageNumber,
                            int numberOfElements, int totalPages) {
        this.response = response;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
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
    public List<T> getPayload() {
        return response.getPayload();
    }

    @Override
    public void setPayload(List<T> payload) {
        response.setPayload(payload);
    }

    @Override
    public int getHttpStatus() {
        return response.getHttpStatus();
    }

    @Override
    public void setHttpStatus(int httpStatus) {
        response.setHttpStatus(httpStatus);
    }

    @Override
    public LocalDateTime getTimestamp() {
        return response.getTimestamp();
    }

    @Override
    public void setTimestamp(LocalDateTime timestamp) {
        response.setTimestamp(timestamp);
    }

    @Override
    public String getMessage() {
        return response.getMessage();
    }

    @Override
    public void setMessage(String message) {
        response.setMessage(message);
    }

    public ResponseEntity<PageableResponse<T>> buildResponseEntity(
            HttpStatus httpStatus, String message, List<T> payload) {
        response.setHttpStatus(httpStatus.value());
        response.setTimestamp(LocalDateTime.now());
        response.setMessage(message);
        response.setPayload(payload);
        return new ResponseEntity<>(this, httpStatus);
    }
}
