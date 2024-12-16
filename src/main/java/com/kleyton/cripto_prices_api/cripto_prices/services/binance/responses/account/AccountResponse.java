package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AccountResponse {
    @JsonProperty("balances")
    private List<BalanceResponse> balancesResponse;

    @JsonIgnore
    public List<BalanceResponse> getFilteredBalances(){
        return this.balancesResponse.stream()
                .filter(b -> !b.isZeroQuantity()).toList();
    }

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getFilteredBalances()
                .stream()
                .map(b ->
                        Asset.builder()
                                .symbol(b.getAsset())
                                .quantity(b.getTotal())
                                .build()
                )
                .toList();
    }
}
