package com.kleyton.cripto_prices_api.cripto_prices.services.cardano;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.ApiError;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses.AddressResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.BlockfrostService;
import com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses.AccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.price.PriceService;
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

    public CardanoResponse getAddressTotalBalance(String address) {
        try {
            AddressResponse addressResponse = blockfrostService.getAddressBalances(address);
            AccountResponse accountResponse = blockfrostService.getStakingAccountBalances(addressResponse.getStakeAddress());
            Double price = priceService.getPrice("ADAUSDT");

            BigInteger totalQuantityAdas = addressResponse.getQuantityAdas().add(accountResponse.getControlledAmount());
            Double totalValue = price * totalQuantityAdas.doubleValue();

            return CardanoResponse.builder()
                    .stakeAddress(addressResponse.getStakeAddress())
                    .asset(new Asset("ADAUSDT", totalQuantityAdas.doubleValue(), totalValue))
                    .build();

        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

    public Double getStakingBalance(String address) {
        try {
            CardanoResponse cardanoResponse = this.getAddressTotalBalance(address);
            AccountResponse accountResponse = blockfrostService.getStakingAccountBalances(cardanoResponse.getStakeAddress());
            Double price = priceService.getPrice("ADAUSDT");
            return price * accountResponse.getControlledAmount().doubleValue();
        } catch (HttpClientErrorException.BadRequest e) {
            String errorResponse = e.getResponseBodyAsString();
            throw new RuntimeException(ApiError.UNEXPECTED_ERROR.getMessage(errorResponse), e);
        }
    }

}
