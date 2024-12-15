package com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.position;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LockedPositionsResponse {
    private Integer total;

    @JsonProperty("rows")
    private List<LockedRowResponse> rowsResponse;
}
