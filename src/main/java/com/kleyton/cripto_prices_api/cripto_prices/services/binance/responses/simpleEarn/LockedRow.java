package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LockedRow {
    private Double amount;
    private String asset;
}
