package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioResponse {
    @JsonProperty("breakdown")
    private BreakdownResponse breakdownResponse;

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getBreakdownResponse().getSpotPositionsResponse()
                .stream()
                .map(sp ->
                        Asset.builder()
                                .symbol(sp.getAsset())
                                .quantity(sp.getTotalBalanceCrytpo())
                                .valueInDollars(sp.getTotalBalanceFiat())
                                .build()
                )
                .toList();
    }
}
