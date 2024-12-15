package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AddressResponse {
    @JsonProperty("amount")
    private List<AmountResponse> amountResponse;
    @JsonProperty("stake_address")
    private String stakeAddress;

    public BigInteger getQuantityAdas(){
        final String LOVELACE_NAME = "lovelace";
        AmountResponse amountResponseLovelace = amountResponse.stream()
                .filter(a -> a.getUnit().equals(LOVELACE_NAME))
                .findFirst().orElse(null);
        if(amountResponseLovelace == null){
            return BigInteger.valueOf(0);
        }
        return amountResponseLovelace.getQuantity().divide(BigInteger.valueOf(1000000));
    }
}
