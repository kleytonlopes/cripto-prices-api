package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Balance {
    private String asset;
    private Double free;
    private Double locked;

    @JsonIgnore
    public double getTotalQuantity(){
        return this.getFree() + this.getLocked();
    }
}
