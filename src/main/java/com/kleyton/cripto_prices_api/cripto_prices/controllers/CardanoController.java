package com.kleyton.cripto_prices_api.cripto_prices.controllers;

import com.kleyton.cripto_prices_api.cripto_prices.exceptions.InvalidSymbolException;
import com.kleyton.cripto_prices_api.cripto_prices.services.cardano.CardanoResponse;
import com.kleyton.cripto_prices_api.cripto_prices.services.cardano.CardanoService;
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

@Tag(name = "Cardano", description = "Acesso aos dados da Main Net da Cardano")
@RestController
@RequestMapping("cardano")
public class CardanoController {

    @Autowired
    CardanoService cardanoService;

    @Operation(summary = "Obtém o saldo atual da cardano em dólares em staking de um endereço.",
            description = "Retorna o saldo atual da ADA Cardano  em staking do endereço fornecido. O endereço deve ser válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Endereço inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/staking/balance")
    public ResponseEntity<?> getStakingBalance(
            @Parameter(description = "Endereço para o qual o saldo será retornado",
                    example = "addr1q99ky4pcqmszp7cvddudvypnxmz6mwk38hcjf8wg06gyk9sc25c" +
                            "plnq5wge79hq0gxvafx0lgp9vu9mewkehj2zg7f9q9tfacj")
            @RequestParam String address) {
        try {
            Double response = cardanoService.getStakingBalance(address);
            return ResponseEntity.ok(response);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtém o saldo atual da cardano em dólares de um endereço.",
            description = "Retorna o saldo atual da ADA Cardano do endereço fornecido. O endereço deve ser válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Endereço inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("/address/balance")
    public ResponseEntity<?> getAddressBalance(
            @Parameter(description = "Endereço para o qual o saldo será retornado",
                    example = "addr1q99ky4pcqmszp7cvddudvypnxmz6mwk38hcjf8wg06gyk9sc25c" +
                            "plnq5wge79hq0gxvafx0lgp9vu9mewkehj2zg7f9q9tfacj")
            @RequestParam String address) {
        try {
            Double response = cardanoService.getAddressBalance(address);
            return ResponseEntity.ok(response);
        } catch (InvalidSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
