package com.kleyton.cripto_prices_api.cripto_prices.services;

public class BinancePriceResponse {
    private String symbol;
    private String price;

    // Getters e Setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
