package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.kucoinEarn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KucoinEarnResponse {
    @JsonProperty("data")
    private Data data;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getData().getItem()
                .stream()
                .map(item -> new Asset(item.getCurrency(), item.getHoldAmount()))
                .toList();
    }
}
