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
public class BreakdownResponse {
    @JsonProperty("portfolio")
    private Portfolio portfolio;

    @JsonProperty("portfolio_balances")
    private PortfolioBalancesResponse portfolioBalancesResponse;

    @JsonProperty("spot_positions")
    private List<SpotPositionResponse> spotPositionsResponse;


    public List<SpotPositionResponse> getSpotPositionsResponse() {
        return this.spotPositionsResponse.stream()
                .filter(sp -> sp.getTotalBalanceFiat() > 1.0)
                .toList();
    }

}
