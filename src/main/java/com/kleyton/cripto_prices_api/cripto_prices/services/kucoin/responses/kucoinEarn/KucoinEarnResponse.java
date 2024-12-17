package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.kucoinEarn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KucoinEarnResponse {
    @JsonProperty("data")
    private DataResponse dataResponse;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getDataResponse().getItemResponses()
                .stream()
                .map(item ->
                        Asset.builder()
                                .symbol(item.getCurrency())
                                .quantity(item.getHoldAmount())
                                .build()
                )
                .toList();
    }
}
