package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin;

import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.account.AccountsResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.kucoinEarn.KucoinEarnResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.price.KucoinPriceResponse;
import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
import com.kleyton.cripto_prices_api.cripto_prices.utils.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class KucoinService {
    private final String KUCOIN_BASE_URL = "https://api.kucoin.com/api/v1";

    @Value("${kucoin.api.key}")
    private String apiKey;

    @Value("${kucoin.secret.key}")
    private String secretKey;

    @Value("${kucoin.api.passphrase}")
    private String passphrase;

    @Autowired
    private RestTemplate restTemplate;

    public KucoinPriceResponse getPrice(String symbol) throws HttpClientErrorException.BadRequest{
        final String KUCOIN_PATH_PRICE = "/market/orderbook/level1";
        String url = KUCOIN_BASE_URL + KUCOIN_PATH_PRICE;

        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("symbol", symbol)
                .build()
                .toUri();
        return restTemplate.exchange(uri, HttpMethod.GET,null, KucoinPriceResponse.class)
                .getBody();
    }

    public AccountsResponse getAccounts(){
        final String KUCOIN_PATH_ACCOUNT = "/accounts";
        final String url = KUCOIN_BASE_URL + KUCOIN_PATH_ACCOUNT;
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        URI uri = UriComponentsBuilder.fromUriString(url)
                .build()
                .toUri();
        String signature = sign(uri, timestamp);
        String signedPassphrse = SignatureUtil.createSignatureBytesEncoded64(secretKey, passphrase);
        HttpEntity<?> entity = getHttpEntity(signature, signedPassphrse, timestamp);

        return restTemplate.exchange(url,
                HttpMethod.GET, entity, AccountsResponse.class).getBody();
    }

    public SummaryResponse getSummary(){
        AccountsResponse accounts= this.getAccounts();
        KucoinEarnResponse kucoinEarnItems = this.getKucoinEarnItems();

        List<Asset> assets = new ArrayList<>();
        assets.addAll(accounts.toAssetList());
        assets.addAll(kucoinEarnItems.toAssetList());

        //se for Stable Coin, o preço do ativo já é em dólares
        assets.stream()
                .filter(a -> a.getSymbol().contains("USD"))
                .forEach(a -> a.setValueInDollars(a.getQuantity()));

        //Settar preço em dólares nos Assets
        for(Asset asset: assets){
            if(asset.getValueInDollars() == null){
                try{
                    KucoinPriceResponse priceResponse = this.getPrice(asset.getSymbol()+"-USDT");
                    asset.setValueInDollars(priceResponse.getDataResponse().getPrice() * asset.getQuantity());
                }catch (Exception e){}
            }
        }
        return new SummaryResponse(assets);
    }

    public KucoinEarnResponse getKucoinEarnItems(){
        final String KUCOIN_PATH_EARN = "/earn/hold-assets";
        final String url = KUCOIN_BASE_URL + KUCOIN_PATH_EARN;
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        URI uri = UriComponentsBuilder.fromUriString(url)
                .build()
                .toUri();
        String signature = sign(uri, timestamp);
        String signedPassphrse = SignatureUtil.createSignatureBytesEncoded64(secretKey, passphrase);
        HttpEntity<?> entity = getHttpEntity(signature, signedPassphrse, timestamp);

        return restTemplate.exchange(url,
                HttpMethod.GET, entity, KucoinEarnResponse.class).getBody();
    }

    private String sign(URI uri, String timestamp) {
        String endpoint = uri.getPath();
        String queryParams = Objects.requireNonNullElse(uri.getQuery(), "");
        String originToSign = String.join("", timestamp, "GET", endpoint,
                queryParams.isEmpty() ? "" : "?" + queryParams);
        return SignatureUtil.createSignatureBytesEncoded64(secretKey, originToSign);
    }

    private HttpEntity<?> getHttpEntity(String signature,String signedPassphrase, String timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("KC-API-KEY", apiKey);
        headers.set("KC-API-SIGN", signature);
        headers.set("KC-API-TIMESTAMP", timestamp);
        headers.set("KC-API-PASSPHRASE", signedPassphrase);
        headers.set("KC-API-KEY-VERSION", "2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
}
