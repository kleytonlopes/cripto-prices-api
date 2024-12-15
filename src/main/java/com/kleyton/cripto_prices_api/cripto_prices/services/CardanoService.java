package com.kleyton.cripto_prices_api.cripto_prices.services;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.ApiError;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.AddressResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.BlockfrostService;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.StakingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;

@Service
public class CardanoService {

    @Autowired
    PriceService priceService;

    @Autowired
    BlockfrostService blockfrostService;

    public CardanoResponse getAddressBalance(String address) {
        try {
            AddressResponse addressResponse = blockfrostService.getAddressBalance(address);
            StakingResponse stakingResponse = blockfrostService.getStakingBalance(addressResponse.getStakeAddress());
            Double price = priceService.getPrice("ADAUSDT");

            BigInteger totalQuantityAdas = addressResponse.getQuantityAdas().add(stakingResponse.getControlledAmount());
            Double totalValue = price * totalQuantityAdas.doubleValue();

            return CardanoResponse.builder()
                    .stakeAddress(addressResponse.getStakeAddress())
                    .valueInDollars(totalValue)
                    .build();

        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

    public Double getStakingBalance(String address) {
        try {
            CardanoResponse cardanoResponse = this.getAddressBalance(address);
            StakingResponse stakingResponse = blockfrostService.getStakingBalance(cardanoResponse.getStakeAddress());
            Double price = priceService.getPrice("ADAUSDT");
            return price * stakingResponse.getControlledAmount().doubleValue();
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

}
