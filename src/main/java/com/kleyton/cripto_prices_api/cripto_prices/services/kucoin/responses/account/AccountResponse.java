package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AccountResponse {
    private String currency;
    private Double available;
    private Double balance;
    private Double holds;
    private String type;
}
