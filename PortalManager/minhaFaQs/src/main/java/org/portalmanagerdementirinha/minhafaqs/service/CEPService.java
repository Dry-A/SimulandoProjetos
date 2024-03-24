package org.portalmanagerdementirinha.minhafaqs.service;


import org.portalmanagerdementirinha.minhafaqs.requisicao.CEPRequest;
import org.portalmanagerdementirinha.minhafaqs.resposta.CEPResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.CompletableFuture;

@Service
public class CEPService {

    private WebClient webClient = WebClient.create("https://viacep.com.br/");

    public CompletableFuture<CEPResponse> pesquisaCep(CEPRequest meuCepRequest) {
        String url = "/ws/" + meuCepRequest.getCep() + "/json/";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(CEPResponse.class)
                .toFuture();
    }

    public String pesquisaRua(String uf, String cidade, String logradouro) {
        String url = "/ws/" + uf + "/" + cidade + "/" + logradouro + "/json";
        String jsonResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return jsonResponse;
    }
}
