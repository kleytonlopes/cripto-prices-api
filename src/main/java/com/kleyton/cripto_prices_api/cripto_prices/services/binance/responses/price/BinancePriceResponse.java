package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BinancePriceResponse {
    private String symbol;
    private Double price;
}
