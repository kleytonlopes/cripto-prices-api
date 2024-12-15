package com.kleyton.cripto_prices_api.cripto_prices.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardanoResponse {
    @JsonProperty("value_in_dollars")
    private Double valueInDollars;
    @JsonProperty("stake_address")
    private String stakeAddress;
}
