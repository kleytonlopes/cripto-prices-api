package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponse {
    private Double value;
    private String currency;
}
