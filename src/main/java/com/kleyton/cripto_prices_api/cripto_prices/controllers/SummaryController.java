package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.binance.BinanceService;
import com.kleyton.cripto_prices_api.cripto_prices.services.cardano.CardanoService;
import com.kleyton.cripto_prices_api.cripto_prices.services.coinbase.CoinbaseService;
import com.kleyton.cripto_prices_api.cripto_prices.services.kucoin.KucoinService;
import com.kleyton.cripto_prices_api.cripto_prices.services.mercadoBitcoin.MercadoBitcoinService;
import com.kleyton.cripto_prices_api.cripto_prices.summary.SummaryResponse;
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

@Tag(name = "Summary", description = "Resumo dos meus ativos por corretora ou por rede Blockchain")
@RestController
@RequestMapping("summary")
public class SummaryController {

    @Autowired
    CardanoService cardanoService;

    @Autowired
    private BinanceService binanceService;

    @Autowired
    private CoinbaseService coinbaseService;

    @Autowired
    private KucoinService kucoinService;

    @Autowired
    private MercadoBitcoinService mbService;

    @Operation(summary = "Obtém os saldos em dólares de cada ativo na conta da Binance",
            description = "Retorna os saldos em dólares de cada ativo na conta da Binance.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/binance")
    public ResponseEntity<?> getBinanceSummary() {
        try {
            SummaryResponse summary = binanceService.getSummary();
            return ResponseEntity.ok(summary);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtém os saldos em dólares de cada ativo na conta da Coinbase",
            description = "Retorna os saldos em dólares de cada ativo na conta da Coinbase.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/coinbase")
    public ResponseEntity<?> getCoinbaseTotalBalances() {
        try {
            SummaryResponse summary = coinbaseService.getSummary();
            return ResponseEntity.ok(summary);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtém os saldos em dólares de cada ativo na conta da Kucoin",
            description = "Retorna os saldos em dólares de cada ativo na conta da Kucoin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/kucoin")
    public ResponseEntity<?> getKucoinTotalBalances() {
        try {
            SummaryResponse summary = kucoinService.getSummary();
            return ResponseEntity.ok(summary);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtém os saldos em dólares de cada ativo na conta do Mercado Bitcoin",
            description = "Retorna os saldos em dólares de cada ativo na conta do Mercado Bitcoin.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balanços retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Símbolo inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/mercado-bitcoin")
    public ResponseEntity<?> getMbTotalBalances() {
        try {
            SummaryResponse summary = mbService.getSummary();
            return ResponseEntity.ok(summary);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }


    @Operation(summary = "Obtém o saldo atual da cardano em dólares de um endereço (Soma o saldo do endereço mais o saldo em staking).",
            description = "Retorna o saldo atual da ADA Cardano e o endereço de staking de um endereço de carteira fornecido. O endereço deve ser válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Endereço inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/cardano")
    public ResponseEntity<?> getCardanoTotalBalance(
            @Parameter(description = "Endereço para o qual o saldo e o endereço de staking será retornado",
                    example = "addr1q99ky4pcqmszp7cvddudvypnxmz6mwk38hcjf8wg06gyk9sc25c" +
                            "plnq5wge79hq0gxvafx0lgp9vu9mewkehj2zg7f9q9tfacj")
            @RequestParam String address) {
        try {
            SummaryResponse summary = cardanoService.getSummary(address);
            return ResponseEntity.ok(summary);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
