package com.kleyton.cripto_prices_api.cripto_prices.exceptions;

public enum ApiError {
    PRICE_API_NO_RESULT("A API de consulta de preços não retornou resultado."),
    UNEXPECTED_ERROR("Erro inesperado: %s"),
    INVALID_SYMBOL("Símbolo inválido: %s.");

    private final String message;

    ApiError(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
