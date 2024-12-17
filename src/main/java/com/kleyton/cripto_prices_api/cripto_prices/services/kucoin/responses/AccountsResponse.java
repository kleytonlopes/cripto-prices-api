package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}
