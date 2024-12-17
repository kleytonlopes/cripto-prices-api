package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KucoinPriceResponse {
    @JsonProperty("data")
    private DataResponse dataResponse;
}
