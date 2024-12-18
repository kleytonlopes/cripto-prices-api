package com.kleyton.cripto_prices_api.cripto_prices.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SummaryResponse {
    private List<Asset> assets;

    public List<Asset> getAssets() {
        return assets.stream().filter(a -> !a.isSmallValue()).toList();
    }

    public double getTotalInDollars() {
        return getAssets().stream().mapToDouble(Asset::getValueInDollars).sum();
    }
}
