package com.kleyton.cripto_prices_api.cripto_prices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Asset {
    private String symbol;
    private Double quantity;
    private Double valueInDollars;

    @JsonIgnore
    public boolean isSmallValue(){
        return this.valueInDollars == null || this.valueInDollars < 1.0;
    }
}
