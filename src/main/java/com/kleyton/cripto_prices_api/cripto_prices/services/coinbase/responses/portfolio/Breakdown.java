package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Breakdown {
    @JsonProperty("portfolio")
    private Portfolio portfolio;

    @JsonProperty("portfolio_balances")
    private PortfolioBalances portfolioBalances;

    @JsonProperty("spot_positions")
    private List<SpotPositions> spotPositions;


    public List<SpotPositions> getSpotPositions() {
        return this.spotPositions.stream()
                .filter(sp -> sp.getTotalBalanceFiat() > 1.0)
                .toList();
    }

}
