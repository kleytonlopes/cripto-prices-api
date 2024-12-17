package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.account;

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
public class AccountsResponse {
    @JsonProperty("data")
    private List<AccountResponse> accountsResponse;

    public List<AccountResponse> getAccountsResponse() {
        return accountsResponse.stream()
                .filter(a -> (a.getAvailable() + a.getBalance() + a.getHolds()) > 0)
                .toList();
    }

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getAccountsResponse()
                .stream()
                .map(account ->
                        Asset.builder()
                                .symbol(account.getCurrency())
                                .quantity(account.getAvailable())
                                .build()
                )
                .toList();
    }
}
