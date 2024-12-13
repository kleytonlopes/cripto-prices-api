package com.kleyton.cripto_prices_api.cripto_prices.services.binance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinancePriceResponse {
    private String symbol;
    private String price;
}
