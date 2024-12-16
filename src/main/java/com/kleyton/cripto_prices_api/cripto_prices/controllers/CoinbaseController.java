package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.CoinbaseService;
import com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio.BreakdownResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.responses.portfolio.PortfolioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Coinbase", description = "Resumo dos meus ativos por corretora ou por rede Blockchain")
@RestController
@RequestMapping("coinbase")
public class CoinbaseController {

    @Autowired
    CoinbaseService coinbaseService;

    @Operation(summary = "Obtém a quantidade de cada ativo na conta da Coinbase",
            description = "Retorna a quantidade de cada ativo na conta da Coinbase.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/balances")
    public ResponseEntity<?> getBalances(@Parameter(description = "Uiid do portifólio para o qual os balanços vão ser retornados",
            example = "f198ac9f-c120-550c-9a9f-b83a6ed97cca")
                                             @RequestParam String uiid) {
        try {
            PortfolioResponse accounts = coinbaseService.getPortfolio(uiid);
            return ResponseEntity.ok(accounts);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
