package com.kleyton.cripto_prices_api.cripto_prices.services;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceService {

    @Autowired
    private RestTemplate restTemplate;

    public String getPrice(String symbol) {
        // URL base do endpoint
        String url = "https://api.binance.com/api/v3/ticker/price?symbol=" + symbol;

        try {
            BinancePriceResponse response = restTemplate.getForObject(url, BinancePriceResponse.class);

            if (response != null) {
                return response.getPrice();
            } else {
                throw new RuntimeException("Resposta da API está vazia");
            }
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            if (errorResponse.contains("Invalid symbol")) {
                throw new InvalidSymbolException("Símbolo inválido: " + symbol);
            }
            throw new RuntimeException("Erro inesperado: " + errorResponse, e);
        }
    }
}
