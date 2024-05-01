package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientPageableDto;
import br.com.pbcompass.demoparkapi.web.dto.client.ParkClientResponseDto;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createClient_WithValidData_ReturnsStatus201(){
        ParkClientResponseDto responseDto = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana Maria", "36498080213"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ParkClientResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDto).isNotNull();
        Assertions.assertThat(responseDto.getId()).isNotNull();
        Assertions.assertThat(responseDto.getName()).isEqualTo("Ana Maria");
        Assertions.assertThat(responseDto.getCpf()).isEqualTo("36498080213");

    }

    @Test
    public void createClient_WithCpfAlreadyRegistered_ReturnsErrorMessageWithStatus409(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana Maria", "02797865227"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(409);

    }

    @Test
    public void createClient_WithInvalidEntryData_ReturnsErrorMessageWithStatus422(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana Maria", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana", "36498080213"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana Maria", "364.980.802-13"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }

    @Test
    public void createClient_WithAdminUserPermission_ReturnsErrorMessageWithStatus403(){
        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"bia@email.com", "123456"))
                .bodyValue(new ParkClientCreateDto("Ana Maria", "02797865227"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(403);

    }

    @Test
    public void findClientById_WithAdminUserPermission_ReturnsClientDtoWithStatus200(){
        ParkClientResponseDto response = testClient
                .get()
                .uri("/api/v1/clients/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkClientResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(11);
        Assertions.assertThat(response.getName()).isEqualTo("Bianca Silva");
        Assertions.assertThat(response.getCpf()).isEqualTo("02797865227");

    }

    @Test
    public void findClientById_WithIdNotRegistered_ReturnsErrorMessageWithStatus404(){
        ErrorMessage response = testClient
                .get()
                .uri("/api/v1/clients/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }

    @Test
    public void findClientById_WithoutAdminPermission_ReturnsErrorMessageWithStatus403(){
        ErrorMessage response = testClient
                .get()
                .uri("/api/v1/clients/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(403);

    }

    @Test
    public void findAll_WithValidEntryData_ReturnsClientsWithStatus200(){
        ParkClientPageableDto response = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkClientPageableDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent().size()).isEqualTo(2);
        Assertions.assertThat(response.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(response.getNumber()).isEqualTo(0);
        Assertions.assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void findAll_WithValidEntryDataAndPagination_ReturnsClientsWithStatus200() {
        ParkClientPageableDto response = testClient
                .get()
                .uri("/api/v1/clients?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkClientPageableDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(response.getNumber()).isEqualTo(1);
        Assertions.assertThat(response.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void findAll_WithUserWithoutPermission_ReturnsErrorMessageWithStatus403() {
        ErrorMessage response = testClient
                .get()
                .uri("/api/v1/clients")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }
}
