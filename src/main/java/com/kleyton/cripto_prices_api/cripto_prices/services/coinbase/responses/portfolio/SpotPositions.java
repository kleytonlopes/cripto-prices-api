package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotPositions {
    private String asset;
    @JsonProperty("account_uuid")
    private String accountUuid;
    @JsonProperty("total_balance_fiat")
    private Double totalBalanceFiat;
    @JsonProperty("total_balance_crypto")
    private Double totalBalanceCrytpo;
}
