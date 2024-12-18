package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioResponse {
    @JsonProperty("breakdown")
    private Breakdown breakdown;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getBreakdown().getSpotPositions()
                .stream()
                .map(sp -> new Asset(sp.getAsset(), sp.getTotalBalanceCrytpo(), sp.getTotalBalanceFiat()))
                .toList();
    }
}
