package com.marketplacehn.response;

import java.time.LocalDateTime;
import java.util.List;

public interface IPageResponse<T> extends Response<List<T>> {
    int getPageSize();
    void setPageSize(int pageSize);
    int getPageNumber();
    void setPageNumber(int pageNumber);
    int getNumberOfElements();
    void setNumberOfElements(int numberOfElements);
    int getTotalPages();
    void setTotalPages(int totalPages);

}
