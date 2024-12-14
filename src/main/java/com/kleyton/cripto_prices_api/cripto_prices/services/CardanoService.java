package com.kleyton.cripto_prices_api.cripto_prices.services;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.ApiError;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.AddressResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.BlockfrostService;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.StakingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CardanoService {

    @Autowired
    PriceService priceService;

    @Autowired
    BlockfrostService blockfrostService;

    public AddressResponse getAddressBalance(String address) {
        try {
             return blockfrostService.getAddressBalance(address);
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

    public Double getStakingBalance(String address) {
        try {
            AddressResponse addressResponse = this.getAddressBalance(address);
            StakingResponse stakingResponse = blockfrostService.getStakingBalance(addressResponse.getStakeAddress());
            Double price = priceService.getPrice("ADAUSDT");
            return price * stakingResponse.getControlledAmount().doubleValue();
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

}
