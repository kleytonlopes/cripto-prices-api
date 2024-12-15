package com.kleyton.cripto_prices_api.cripto_prices.services.binance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Service
public class BinanceService {

    private final String BINANCE_BASE_URL = "https://api.binance.com/api/v3/ticker";
    private final String BINANCE_PATH_PRICE = "/price";

    @Autowired
    private RestTemplate restTemplate;

    public BinancePriceResponse getPrice(String symbol) throws HttpClientErrorException.BadRequest{
        String url = BINANCE_BASE_URL + BINANCE_PATH_PRICE +  "?symbol={symbol}";
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        return restTemplate.getForObject(url, BinancePriceResponse.class, params);
    }
}
