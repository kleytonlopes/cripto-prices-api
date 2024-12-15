package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost;

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
    private List<Amount> amount;

    @JsonProperty("stake_address")
    private String stakeAddress;

    public BigInteger getQuantityAdas(){
        final String LOVELACE_NAME = "lovelace";
        Amount amountLovelace = amount.stream()
                .filter(a -> a.getUnit().equals(LOVELACE_NAME))
                .findFirst().orElse(null);
        if(amountLovelace == null){
            return BigInteger.valueOf(0);
        }
        return amountLovelace.getQuantity().divide(BigInteger.valueOf(1000000));
    }
}
