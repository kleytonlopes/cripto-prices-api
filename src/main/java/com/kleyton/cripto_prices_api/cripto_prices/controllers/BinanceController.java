package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.account.AccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.BinanceService;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.account.SimpleEarnAccountResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.position.FlexiblePositionsResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.responses.simpleEarn.position.LockedPositionsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Binance", description = "Acesso os dados das criptomoedas que estão na Binance")
@RestController
@RequestMapping("binance")
public class BinanceController {

    @Autowired
    private BinanceService binanceService;

    @Operation(summary = "Obtém os saldos em dólares de cada ativo na conta da Binance",
            description = "Retorna os saldos em dólares de cada ativo na conta da Binance.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/balances")
    public ResponseEntity<?> getBalances() {
        try {
            AccountResponse price = binanceService.getAccount();
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/simple-earn/balances")
    public ResponseEntity<?> getStakingBalances() {
        try {
            SimpleEarnAccountResponse price = binanceService.getSimpleEarnAccount();
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/simple-earn/flexible/positions")
    public ResponseEntity<?> getStakingFlexiblePositions() {
        try {
            FlexiblePositionsResponse price = binanceService.getStakingFlexiblePositions();
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping("/simple-earn/locked/positions")
    public ResponseEntity<?> getStakingLockedPositions() {
        try {
            LockedPositionsResponse price = binanceService.getStakingLockedPositions();
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
