package com.kleyton.cripto_prices_api.cripto_prices.services.binance;

import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account.AccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.price.BinancePriceResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.SimpleEarnAccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.FlexiblePositionsResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.LockedPositionsResponse;
import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
import com.kleyton.cripto_prices_api.cripto_prices.utils.SignatureUtil;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinanceService {

    private final String BINANCE_BASE_URL = "https://api.binance.com";
    private final String BINANCE_PATH_PRICE = "/api/v3/ticker/price";
    private final String BINANCE_PATH_ACCOUNT = "/api/v3/account";
    private final String BINANCE_PATH_SIMPLE_EARN_ACCOUNT = "/sapi/v1/simple-earn/account";
    private final String BINANCE_PATH_SIMPLE_EARN_FLEXIBLE_POSITIONS = "/sapi/v1/simple-earn/flexible/position";
    private final String BINANCE_PATH_SIMPLE_EARN_LOCKED_POSITIONS = "/sapi/v1/simple-earn/locked/position";


    @Value("${binance.api.key}")
    private String binanceApiKey;

    @Value("${binance.secret.key}")
    private String binanceSecretKey;

    @Autowired
    private RestTemplate restTemplate;

    public BinancePriceResponse getPrice(String symbol) throws HttpClientErrorException.BadRequest{
        String url = BINANCE_BASE_URL + BINANCE_PATH_PRICE;

        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("symbol", symbol)
                .build()
                .toUri();
        return restTemplate.exchange(uri, HttpMethod.GET,null, BinancePriceResponse.class)
                .getBody();
    }

    public SummaryResponse getSummary(){
        AccountResponse account = this.getAccount();
        FlexiblePositionsResponse flexiblePositions = this.getStakingFlexiblePositions();
        LockedPositionsResponse lockedPositions = this.getStakingLockedPositions();

        List<Asset> assets = new ArrayList<>();
        assets.addAll(account.toAssetList());
        assets.addAll(flexiblePositions.toAssetList());
        assets.addAll(lockedPositions.toAssetList());

        //remover LDUSDC se houver
        assets.removeIf(a -> a.getSymbol().equals("LDUSDC"));

        //se for Stable Coin, o preço do ativo já é em dólares
        assets.stream()
                .filter(a -> a.getSymbol().contains("USD"))
                .forEach(a -> a.setValueInDollars(a.getQuantity()));

        //Settar preço em dólares nos Assets
        for(Asset asset: assets){
            if(asset.getValueInDollars() == null){
                try{
                    BinancePriceResponse binancePriceResponse = this.getPrice(asset.getSymbol()+"USDT");
                    asset.setValueInDollars(binancePriceResponse.getPrice() * asset.getQuantity());
                }catch (Exception e){}
            }
        }

        return new SummaryResponse(assets);
    }

    public AccountResponse getAccount() throws HttpClientErrorException.BadRequest {
        String url = BINANCE_BASE_URL + BINANCE_PATH_ACCOUNT;
        return restTemplate.exchange(getUriWithSignature(url), HttpMethod.GET, getHttpEntity(), AccountResponse.class)
                .getBody();
    }

    public SimpleEarnAccountResponse getSimpleEarnAccount() throws HttpClientErrorException.BadRequest {
        String url = BINANCE_BASE_URL + BINANCE_PATH_SIMPLE_EARN_ACCOUNT;
        return restTemplate.exchange(getUriWithSignature(url), HttpMethod.GET, getHttpEntity(), SimpleEarnAccountResponse.class)
                .getBody();
    }

    public FlexiblePositionsResponse getStakingFlexiblePositions() throws HttpClientErrorException.BadRequest {
        String url = BINANCE_BASE_URL + BINANCE_PATH_SIMPLE_EARN_FLEXIBLE_POSITIONS;
        return restTemplate.exchange(getUriWithSignature(url), HttpMethod.GET, getHttpEntity(), FlexiblePositionsResponse.class)
                .getBody();
    }

    public LockedPositionsResponse getStakingLockedPositions() throws HttpClientErrorException.BadRequest {
        String url = BINANCE_BASE_URL + BINANCE_PATH_SIMPLE_EARN_LOCKED_POSITIONS;
        return restTemplate.exchange(getUriWithSignature(url), HttpMethod.GET, getHttpEntity(), LockedPositionsResponse.class)
                .getBody();
    }

    private HttpEntity<?> getHttpEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", binanceApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    private URI getUriWithSignature(String url){
        return getUriWithSignature(url, null);
    }

    private URI getUriWithSignature(String url, @Nullable MultiValueMap<String, String> otherParams){
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url)
                .queryParam("recvWindow", "40000")
                .queryParam("timestamp", String.valueOf(System.currentTimeMillis()))
                .queryParams(otherParams)
                .build();
        String queryString = uriComponents.getQuery();
        return UriComponentsBuilder.fromUri(uriComponents.toUri())
                .queryParam("signature", SignatureUtil.createSignatureHex(binanceSecretKey, queryString))
                .build()
                .toUri();
    }
}
