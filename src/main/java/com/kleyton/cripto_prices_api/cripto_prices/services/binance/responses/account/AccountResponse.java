package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    @JsonIgnoreProperties
    public List<BalanceResponse> getFilteredBalances(){
        return this.balancesResponse.stream()
                .filter(b -> !b.isZeroQuantity()).toList();
    }
}
