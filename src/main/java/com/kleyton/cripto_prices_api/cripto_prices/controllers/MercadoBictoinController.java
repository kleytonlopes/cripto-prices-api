package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.MercadoBitcoinService;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.responses.balances.BalancesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Mercado Bitcoin", description = "Resumo dos meus ativos na corretora Mercado Bitcoin")
@RestController
@RequestMapping("mercado-bitcoin")
public class MercadoBictoinController {

    @Autowired
    private MercadoBitcoinService mercadoBitcoinService;

    @Operation(summary = "Obtém a quantidade de cada ativo na conta do Mercado Bitcoin",
            description = "Retorna a quantidade de cada ativo na conta do Mercado Bitcoin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/balances")
    public ResponseEntity<?> getAccount() {
        try {
            BalancesResponse balances = mercadoBitcoinService.getBalances();
            return ResponseEntity.ok(balances);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
