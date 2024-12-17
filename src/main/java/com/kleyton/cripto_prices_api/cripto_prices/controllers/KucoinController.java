package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;

import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.KucoinService;
import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.responses.AccountsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Kucoin", description = "Resumo dos meus ativos na corretora Kucoin")
@RestController
@RequestMapping("kucoin")
public class KucoinController {

    @Autowired
    KucoinService kucoinService;

    @Operation(summary = "Obtém a quantidade de cada ativo na conta da Kucoin",
            description = "Retorna a quantidade de cada ativo na conta da Coinbase.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/balances")
    public ResponseEntity<?> getAccounts() {
        try {
            AccountsResponse accounts = kucoinService.getAccounts();
            return ResponseEntity.ok(accounts);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
