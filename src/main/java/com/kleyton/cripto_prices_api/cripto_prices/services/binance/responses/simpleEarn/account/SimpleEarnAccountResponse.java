package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SimpleEarnAccountResponse {
    private Double totalAmountInUSDT;
    private Double totalFlexibleAmountInUSDT;
    private Double totalLockedInUSDT;
}
