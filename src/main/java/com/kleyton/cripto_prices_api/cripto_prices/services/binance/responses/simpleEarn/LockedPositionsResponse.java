package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn;

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
public class LockedPositionsResponse {
    private Integer total;

    @JsonProperty("rows")
    private List<LockedRow> rowsResponse;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getRowsResponse()
                .stream()
                .map(r -> new Asset(r.getAsset(),r.getAmount()))
                .toList();
    }
}
