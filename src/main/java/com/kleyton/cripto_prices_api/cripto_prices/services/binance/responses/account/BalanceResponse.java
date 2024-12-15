package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BalanceResponse {
    private String asset;
    private Double free;
    private Double locked;
}
