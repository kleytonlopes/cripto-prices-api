package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AddressResponse {
    private List<Amount> amount;

    @JsonProperty("stake_address")
    private String stakeAddress;
}
