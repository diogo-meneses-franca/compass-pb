package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.jwt.JwtToken;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = Objects.requireNonNull(client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new ParkUserLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody()).getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
