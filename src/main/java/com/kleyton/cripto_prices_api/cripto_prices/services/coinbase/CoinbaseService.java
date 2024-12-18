package com.kleyton.cripto_prices_api.cripto_prices.services.coinbase;

import com.kleyton.cripto_prices_api.cripto_prices.summary.Asset;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.BinanceService;
import com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio.PortfolioResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.Instant;

import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
import com.kleyton.cripto_prices_api.cripto_prices.utils.PemUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;


@Service
public class CoinbaseService {

    @Value("${coinbase.api.key}")
    private String coinbaseApiKey;

    @Value("${coinbase.secret.key}")
    private String coinbaseSecretKey;

    @Value("${coinbase.portfolio.uuid}")
    private String coinbasePortfolioUuid;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BinanceService binanceService;

    public PortfolioResponse getPortfolio(String uuid) throws Exception {
        final String COINBASE_BASE_URL = "https://api.coinbase.com";
        final String COINBASE_PATH_PORTFOLIO = "/api/v3/brokerage/portfolios/" + uuid;
        final String url = COINBASE_BASE_URL + COINBASE_PATH_PORTFOLIO;
        final String urlWithoutHttps = url.replace("https://", "");

        return restTemplate.exchange(url,
                HttpMethod.GET, getHttpEntity(urlWithoutHttps), PortfolioResponse.class).getBody();
    }

    public SummaryResponse getSummary() throws Exception{
        PortfolioResponse portfolioResponse = this.getPortfolio(coinbasePortfolioUuid);
        List<Asset> assets = new ArrayList<>(portfolioResponse.toAssetList());
        return new SummaryResponse(assets);
    }

    private HttpEntity<?> getHttpEntity(String url) throws Exception {
        String jwt = generateJwt(coinbaseApiKey,coinbaseSecretKey, url);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    private Map<String, Object> getClainsJwt(String apiKey, String url){
        String requestMethod = "GET";
        String uri = requestMethod + " " + url;

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "cdp");
        claims.put("nbf", Instant.now().getEpochSecond());
        claims.put("exp", Instant.now().getEpochSecond() + 120); // Expiração em 120 segundos
        claims.put("sub", apiKey);
        claims.put("uri", uri);
        return claims;
    }


    private String generateJwt(String apiKey, String apiSecret, String url) throws Exception {
       PrivateKey privateKey = PemUtil.getPrivateKeyFromPEM(apiSecret);
        return Jwts.builder()
                .header()
                    .add("alg","ES256")
                    .add("typ","JWT")
                    .add("kid",apiKey)
                    .add("nonce",String.valueOf(Instant.now().getEpochSecond()))
                    .and()
                .claims(getClainsJwt(apiKey, url))
                .signWith(privateKey) // Assina o JWT com ES256
                .compact();
    }
}
