package com.marketplacehn.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize
public class PaginatedBaseResponse<T> extends BaseResponse<List<T>> implements PaginatedResponse<T> {
    @JsonProperty
    private int pageSize;
    @JsonProperty
    private int pageNumber;
    @JsonProperty
    private long totalElements;
    @JsonProperty
    private int totalPages;

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
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
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
        return super.getPayload();
    }

    @Override
    public void setPayload(List<T> payload) {
        super.setPayload(payload);
    }

    @Override
    public int getHttpStatus() {
        return super.getHttpStatus();
    }

    @Override
    public void setHttpStatus(int httpStatus) {
        super.setHttpStatus(httpStatus);
    }

    @Override
    public LocalDateTime getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(LocalDateTime timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

}
