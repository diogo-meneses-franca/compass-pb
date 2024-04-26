package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.web.dto.UserCreateDTO;
import br.com.pbcompass.demoparkapi.web.dto.UserResponseDTO;
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
        UserResponseDTO userResponseDTO = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(userResponseDTO).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userResponseDTO.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userResponseDTO.getUsername()).isEqualTo("tody@email.com");
        org.assertj.core.api.Assertions.assertThat(userResponseDTO.getRole()).isEqualTo("CLIENT");
    }


}
