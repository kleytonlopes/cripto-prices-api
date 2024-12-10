package com.kleyton.cripto_prices_api.cripto_prices.exceptions;

public class InvalidSymbolException extends RuntimeException {
    public InvalidSymbolException(String message) {
        super(message);
    }
}
