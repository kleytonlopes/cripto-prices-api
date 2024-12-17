package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.kucoinEarn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ItemResponse {
    private String currency;
    private Double holdAmount;
    private String status;
}
