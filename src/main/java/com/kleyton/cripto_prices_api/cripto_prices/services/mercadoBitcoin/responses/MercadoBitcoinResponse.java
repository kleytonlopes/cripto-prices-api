package com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
public class MercadoBitcoinResponse {
    private List<Asset> assets;

    public List<Asset> getAssets() {
        return assets.stream().filter(a -> !a.isSmallValue()).toList();
    }

    public double getTotalInDollars() {
        return getAssets().stream().mapToDouble(Asset::getValueInDollars).sum();
    }
}
