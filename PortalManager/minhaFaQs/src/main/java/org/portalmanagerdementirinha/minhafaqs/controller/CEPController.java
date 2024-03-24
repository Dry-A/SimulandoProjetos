package org.portalmanagerdementirinha.minhafaqs.controller;

import org.portalmanagerdementirinha.minhafaqs.exceptions.CepExceptions;
import org.portalmanagerdementirinha.minhafaqs.requisicao.CEPRequest;
import org.portalmanagerdementirinha.minhafaqs.resposta.CEPResponse;
import org.portalmanagerdementirinha.minhafaqs.service.CEPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/consulta-cep")
public class CEPController {

    @Autowired
    private CEPService meuCepService;

    @GetMapping
    public ResponseEntity<Object> getCep(@RequestBody CEPRequest meuCepRequest) {
        try {
            CompletableFuture<CEPResponse> future = meuCepService.pesquisaCep(meuCepRequest);
            CEPResponse meuCepResponse = future.get(); // Obter o resultado futuro
            return ResponseEntity.ok().body(meuCepResponse);
        } catch (InterruptedException | ExecutionException errinho) {
            return CepExceptions.respostaErro("Deu erro: ", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/rua")
    public String listaRua(@RequestParam("UF") String uf,
                           @RequestParam("CIDADE") String cidade,
                           @RequestParam("LOGRADOURO") String logradouro) {
        return meuCepService.pesquisaRua(uf, cidade, logradouro);
    }
}
