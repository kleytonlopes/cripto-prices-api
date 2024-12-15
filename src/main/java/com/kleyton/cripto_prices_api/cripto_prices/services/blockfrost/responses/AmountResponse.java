package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class AmountResponse {
    private String unit;
    private BigInteger quantity;
}
