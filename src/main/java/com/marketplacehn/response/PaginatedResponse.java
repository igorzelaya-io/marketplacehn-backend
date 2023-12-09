package com.marketplacehn.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PaginatedResponse<T> extends Response<List<T>> {
    int getPageNumber();
    void setPageNumber(int pageNumber);
    int getPageSize();
    void setPageSize(int pageSize);
    long getTotalElements();
    void setTotalElements(long totalElements);
    int getTotalPages();
    void setTotalPages(int totalPages);

    default ResponseEntity<? extends PaginatedResponse<T>> buildPaginatedResponseEntity(
            HttpStatus httpStatus, String message, List<T> payload,
            int pageNumber, int pageSize, long totalElements,  int totalPages) {

        setPayload(payload);
        setHttpStatus(httpStatus.value());
        setTimestamp(LocalDateTime.now());
        setMessage(message);
        setPageNumber(pageNumber);
        setPageSize(pageSize);
        setTotalElements(totalElements);
        setTotalPages(totalPages);

        return new ResponseEntity<>(this, httpStatus);
    }
}
