package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.position;

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
public class FlexiblePositionsResponse {
    private Integer total;

    @JsonProperty("rows")
    private List<FlexibleRowResponse> rowsResponse;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getRowsResponse()
                .stream()
                .map(r ->
                        Asset.builder().symbol(r.getAsset()).quantity(r.getTotalAmount()).build()
                )
                .toList();
    }
}
