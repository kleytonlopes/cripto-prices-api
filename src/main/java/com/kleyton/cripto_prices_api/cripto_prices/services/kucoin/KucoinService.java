package com.kleyton.cripto_prices_api.cripto_prices.services.kucoin;

import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.AccountsResponse;
import com.kleyton.cripto_prices_api.cripto_prices.utils.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;

@Service
public class KucoinService {

    @Value("${kucoin.api.key}")
    private String apiKey;

    @Value("${kucoin.secret.key}")
    private String secretKey;

    @Value("${kucoin.api.passphrase}")
    private String passphrase;

    @Autowired
    private RestTemplate restTemplate;

    public AccountsResponse getAccounts(){
        final String KUCOIN_BASE_URL = "https://api.kucoin.com";
        final String KUCOIN_PATH_PORTFOLIO = "/api/v1/accounts";
        final String url = KUCOIN_BASE_URL + KUCOIN_PATH_PORTFOLIO;
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
