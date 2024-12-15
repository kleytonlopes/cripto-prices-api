package com.kleyton.cripto_prices_api.cripto_prices.services.cardano;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardanoResponse {
    private Asset asset;

    @JsonProperty("stake_address")
    private String stakeAddress;
}
