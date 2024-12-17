package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.kucoinEarn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DataResponse {
    private Integer totalNum;
    @JsonProperty("items")
    private List<ItemResponse> itemResponses;
}
