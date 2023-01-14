package com.marketplacehn.response;

import java.util.List;

public interface IPageResponse<T>{

    List<T> getPayloadPage();
    void setPayloadPage(List<T> payloadPage);

    int getPageSize();
    void setPageSize(int pageSize);

    int getPageNumber();
    void setPageNumber(int pageNumber);

    int getNumberOfElements();
    void setNumberOfElements(int numberOfElements);

    int getTotalPages();
    void setTotalPages(int totalPages);

}
