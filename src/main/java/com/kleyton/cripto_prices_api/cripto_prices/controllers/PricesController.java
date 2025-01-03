package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.price.PriceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Price", description = "Acesso aos preços das criptomoedas")
@RestController
@RequestMapping
public class PricesController {

    @Autowired
    private PriceService priceService;

    @Operation(summary = "Obtém o preço de um símbolo na corretora Binance",
            description = "Retorna o preço do símbolo fornecido. O símbolo deve ser válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preço retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/binance/price")
    public ResponseEntity<?> getBinancePrice(
            @Parameter(description = "Símbolo para o qual o preço será retornado", example = "BTCUSDT")
            @RequestParam String symbol) {
        try {
            Double price = priceService.getBinancePrice(symbol);
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtém o preço de um símbolo na corretora Kucoin.",
            description = "Retorna o preço do símbolo fornecido. O símbolo deve ser válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preço retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/kucoin/price")
    public ResponseEntity<?> getKucoinPrice(
            @Parameter(description = "Símbolo para o qual o preço será retornado", example = "BTC-USDT")
            @RequestParam String symbol) {
        try {
            Double price = priceService.getKucoinPrice(symbol);
            return ResponseEntity.ok(price);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
