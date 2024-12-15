package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class Amount {
    private String unit;
    private BigInteger quantity;
}
