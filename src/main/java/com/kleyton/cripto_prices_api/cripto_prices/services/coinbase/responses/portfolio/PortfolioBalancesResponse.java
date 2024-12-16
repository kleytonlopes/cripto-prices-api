package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioBalancesResponse {
    @JsonProperty("total_balance")
    private BalanceResponse balanceResponse;
}
