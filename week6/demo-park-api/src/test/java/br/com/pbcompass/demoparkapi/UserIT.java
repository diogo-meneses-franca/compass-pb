package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.web.dto.ParkUserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserPasswordDTO;
import br.com.pbcompass.demoparkapi.web.dto.ParkUserResponseDTO;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_WithValidUsernameAndPassword_ReturnsCreatedUserWithStatus201() {
        ParkUserResponseDTO parkUserResponseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ParkUserResponseDTO.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getUsername()).isEqualTo("tody@email.com");
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void createUser_WithInvalidUsername_ReturnsErrorMessage422() {
        ErrorMessage errorMessage = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUser_WithInvalidPassword_ReturnsErrorMessage422() {
        ErrorMessage errorMessage = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("tody@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        errorMessage = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("tody@email.com", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        errorMessage = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("tody@email.com", "1234"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUser_RepeatedUsername_ReturnsErrorMessage409() {
        ErrorMessage errorMessage = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkUserCreateDTO("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(409);
    }

    @Test
    public void findUserById_WithValidId_ReturnsUserWithStatus200() {
        ParkUserResponseDTO parkUserResponseDTO = testClient
                .get()
                .uri("/api/v1/users/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkUserResponseDTO.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getUsername()).isEqualTo("ana@email.com");
        org.assertj.core.api.Assertions.assertThat(parkUserResponseDTO.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void findUserById_WithInvalidId_ReturnsUserErrorMessageWithStatus404() {
        ErrorMessage errorMessage = testClient
                .get()
                .uri("/api/v1/users/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(404);
    }

    @Test
    public void updateUserPassword_WithValidData_ReturnsStatusCode204() {
        testClient
                .patch()
                .uri("/api/v1/users/100")
                .bodyValue(new ParkUserPasswordDTO("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void updateUserPassword_WithInvalidId_ReturnsErrorMessageWithStatus404() {
        ErrorMessage errorMessage = testClient
                .patch()
                .uri("/api/v1/users/0")
                .bodyValue(new ParkUserPasswordDTO("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(404);
    }

    @Test
    public void updateUserPassword_WithInvalidDataEntrySize_ReturnsErrorMessageWithStatus422() {
        ErrorMessage errorMessage = testClient
                .patch()
                .uri("/api/v1/users/0")
                .bodyValue(new ParkUserPasswordDTO("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        testClient
                .patch()
                .uri("/api/v1/users/0")
                .bodyValue(new ParkUserPasswordDTO("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);

        testClient
                .patch()
                .uri("/api/v1/users/0")
                .bodyValue(new ParkUserPasswordDTO("12345678", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(422);
    }

    @Test
    public void updateUserPassword_WithInvalidPasswords_ReturnsErrorMessageWithStatus400() {
        ErrorMessage errorMessage = testClient
                .patch()
                .uri("/api/v1/users/100")
                .bodyValue(new ParkUserPasswordDTO("123456", "123456", "000000"))
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(500);

        testClient
                .patch()
                .uri("/api/v1/users/100")
                .bodyValue(new ParkUserPasswordDTO("123456", "000000", "123456"))
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMessage).isNotNull();
        Assertions.assertThat(errorMessage.getStatus()).isEqualTo(500);
    }




}
