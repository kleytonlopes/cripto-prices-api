package com.kleyton.cripto_prices_api.cripto_prices.services.price;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.ApiError;
import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.price.PriceResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.BinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class PriceService {

    @Autowired
    private BinanceService binanceService;

    public Double getPrice(String symbol) {
        try {
            PriceResponse response = binanceService.getPrice(symbol);
            if (response != null) {
                return response.getPrice();
            } else {
                throw new RuntimeException(ApiError.PRICE_API_NO_RESULT.getMessage());
            }
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            if (errorResponse.contains("Invalid symbol")) {
                throw new InvalidSymbolException(ApiError.INVALID_SYMBOL.getMessage(symbol));
            }
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }
}