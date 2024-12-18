package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class AccountResponse {

    @JsonProperty("balances")
    private List<Balance> balances;

    public List<Balance> getBalances(){
        return this.balances.stream()
                .filter(b -> b.getTotalQuantity() > 0).toList();
    }

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getBalances()
                .stream()
                .map(b -> new Asset(b.getAsset(), b.getTotalQuantity()))
                .toList();
    }
}
