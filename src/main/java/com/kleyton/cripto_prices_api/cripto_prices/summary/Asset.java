package com.kleyton.cripto_prices_api.cripto_prices.summary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Asset {
    private String symbol;
    private Double quantity;
    private Double valueInDollars;

    public Asset(String symbol, Double quantity){
        this.setSymbol(symbol);
        this.setQuantity(quantity);
    }

    @JsonIgnore
    public boolean isSmallValue(){
        return this.valueInDollars == null || this.valueInDollars < 1.0;
    }
}
