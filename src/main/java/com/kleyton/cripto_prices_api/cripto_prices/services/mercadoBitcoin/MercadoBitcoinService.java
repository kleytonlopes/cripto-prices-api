package com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin;

import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.authorize.AuthorizationResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.balances.Balance;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.balances.BalancesResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.price.PriceService;
import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoBitcoinService {
    final String MB_BASE_URL = "https://api.mercadobitcoin.net/api/v4";

    @Value("${mb.api.key}")
    private String apiKey;

    @Value("${mb.secret.key}")
    private String secretKey;

    @Value("${mb.account.id}")
    private String accountId;

    @Autowired
    private PriceService priceService;

    @Autowired
    private RestTemplate restTemplate;


    public SummaryResponse getSummary() throws Exception{
        BalancesResponse balances = this.getBalances();
        List<Asset> assets = new ArrayList<>(balances.toAssetList());

        //se for Stable Coin, o preço do ativo já é em dólares
        assets.stream()
                .filter(a -> a.getSymbol().contains("USD"))
                .forEach(a -> a.setValueInDollars(a.getQuantity()));

        //Settar preço em dólares nos Assets
        for(Asset asset: assets){
            if(asset.getValueInDollars() == null){
                try{
                    Double price = priceService.getBinancePrice(asset.getSymbol()+"USDT");
                    asset.setValueInDollars(price * asset.getQuantity());
                }catch (Exception e){}
            }
        }
        return new SummaryResponse(assets);
    }

    public BalancesResponse getBalances() throws Exception {
        final String MB_ACCOUNT_PATH = "/accounts/"+accountId+"/balances";
        String url = MB_BASE_URL + MB_ACCOUNT_PATH;
        AuthorizationResponse authorizeResponse = this.login();
        HttpEntity<?> entity = getHttpEntity(authorizeResponse.getAccessToken());
        Balance[] balances = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Balance[].class
        ).getBody();
        return new BalancesResponse(balances);
    }

    private AuthorizationResponse login() {
        final String MB_AUTHORIZE_PATH = "/authorize";
        String url = MB_BASE_URL + MB_AUTHORIZE_PATH;
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", apiKey);
        body.add("password", secretKey);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body,null),
                AuthorizationResponse.class
        ).getBody() ;
    }

    private HttpEntity<?> getHttpEntity(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
}
