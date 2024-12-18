package com.kleyton.cripto_prices_api.cripto_prices.services.cardano;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.ApiError;
import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses.AddressResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.BlockfrostService;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses.AccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.price.PriceService;
import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CardanoService {

    @Autowired
    PriceService priceService;

    @Autowired
    BlockfrostService blockfrostService;

    public SummaryResponse getSummary(String address) {
        try {
            AddressResponse addressResponse = blockfrostService.getAddressBalances(address);
            AccountResponse accountResponse = blockfrostService
                    .getStakingAccountBalances(addressResponse.getStakeAddress());
            Double price = priceService.getBinancePrice("ADAUSDT");

            BigInteger totalQuantityAdas = addressResponse
                    .getQuantityAdas()
                    .add(accountResponse.getControlledAmount());
            Double totalValue = price * totalQuantityAdas.doubleValue();

            Asset asset = new Asset("ADA", totalQuantityAdas.doubleValue(), totalValue);
            return new SummaryResponse(List.of(asset));
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

    public Double getAddressBalance(String address) {
        try {
            AddressResponse addressResponse = blockfrostService.getAddressBalances(address);
            Double price = priceService.getBinancePrice("ADAUSDT");
            return price * addressResponse.getQuantityAdas().doubleValue();
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

    public Double getStakingBalance(String address) {
        try {
            AddressResponse addressResponse = blockfrostService.getAddressBalances(address);
            AccountResponse accountResponse = blockfrostService
                    .getStakingAccountBalances(addressResponse.getStakeAddress());
            Double price = priceService.getBinancePrice("ADAUSDT");
            return price * accountResponse.getControlledAmount().doubleValue();
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

}
