package com.kleyton.cripto_prices_api.cripto_prices.services.blockfrost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlockfrostService {
    private final String BLOCKFROST_BASE_URL = "https://cardano-mainnet.blockfrost.io/api/v0/";
    private final String BLOCKFROST_PATH_ADDRESS = "/addresses/{address}";
    private final String BLOCKFROST_PATH_ACCOUNTS = "/accounts/{address}";

    @Value("${blockfrost.project.key}")
    private String blockfrostApiKey;

    @Autowired
    private RestTemplate restTemplate;

    public StakingResponse getStakingBalance(String stakingAddress) throws HttpClientErrorException.BadRequest{
        String url = BLOCKFROST_BASE_URL + BLOCKFROST_PATH_ACCOUNTS;

        HttpHeaders headers = new HttpHeaders();
        headers.set("project_id", blockfrostApiKey);
        HttpEntity entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("address", stakingAddress);

        return restTemplate.exchange(url, HttpMethod.GET ,entity, StakingResponse.class, params).getBody();
    }

    public AddressResponse getAddressBalance(String address) throws HttpClientErrorException.BadRequest{
        String url = BLOCKFROST_BASE_URL + BLOCKFROST_PATH_ADDRESS;

        HttpHeaders headers = new HttpHeaders();
        headers.set("project_id", blockfrostApiKey);
        HttpEntity entity = new HttpEntity<>(headers);


        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        return restTemplate.exchange(url, HttpMethod.GET ,entity, AddressResponse.class, params).getBody();
    }
}
