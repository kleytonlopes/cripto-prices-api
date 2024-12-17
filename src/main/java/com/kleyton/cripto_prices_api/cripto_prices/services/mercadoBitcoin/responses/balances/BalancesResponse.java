package com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.balances;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kleyton.cripto_prices_api.cripto_prices.models.Asset;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class BalancesResponse {
    private BalanceResponse[] balances;

    public List<BalanceResponse> getBalances() {
        List<BalanceResponse> balanceList = Arrays.asList(balances);
        return balanceList.stream().filter(b -> b.getTotal() > 0).toList();
    }

    @JsonIgnore
    public List<Asset> toAssetList(){
        return this.getBalances()
                .stream()
                .map(b ->
                        Asset.builder()
                                .symbol(b.getSymbol())
                                .quantity(b.getTotal())
                                .build()
                )
                .toList();
    }
}
