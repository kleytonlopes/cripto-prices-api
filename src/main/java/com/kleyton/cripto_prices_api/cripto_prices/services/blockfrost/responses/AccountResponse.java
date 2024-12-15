package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AccountResponse {
    @JsonProperty("controlled_amount")
    private BigInteger controlledAmount;

    public BigInteger getControlledAmount() {
        return controlledAmount.divide(BigInteger.valueOf(1000000));
    }
}
