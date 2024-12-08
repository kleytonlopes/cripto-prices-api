package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricesController {

    @GetMapping("/hello")
   String hello() {
        return "Olá Todo Mundo!!!";
    }
}
