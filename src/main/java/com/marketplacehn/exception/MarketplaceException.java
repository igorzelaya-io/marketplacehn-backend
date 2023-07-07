package com.marketplacehn.exception;


public class MarketplaceException extends RuntimeException {


    public MarketplaceException(String message) {
        super(message);
    }

    public MarketplaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarketplaceException(Throwable cause) {
        super(cause);
    }

    protected MarketplaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
