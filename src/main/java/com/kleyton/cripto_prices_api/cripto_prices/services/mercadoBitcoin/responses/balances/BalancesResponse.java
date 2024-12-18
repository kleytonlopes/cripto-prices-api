package com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.balances;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class BalancesResponse {
    private Balance[] balances;

    public List<Balance> getBalances() {
        List<Balance> balanceList = Arrays.asList(balances);
        return balanceList.stream().filter(b -> b.getTotal() > 0).toList();
    }

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getBalances()
                .stream()
                .map(b -> new Asset(b.getSymbol(), b.getTotal()))
                .toList();
    }
}
