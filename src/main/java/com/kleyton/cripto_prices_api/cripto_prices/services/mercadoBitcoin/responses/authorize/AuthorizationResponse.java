package com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.authorize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AuthorizationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expiration")
    private String expiration;
}
