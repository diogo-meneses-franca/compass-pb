package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.web.dto.parkingspace.ParkingSpaceCreateDto;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking-spaces/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking-spaces/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingSpaceIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void saveParkingSpace_WithValidEntryData_ReturnLocationWithStatus201(){
        testClient
                .post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-07", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader()
                .exists(HttpHeaders.LOCATION);
    }

    @Test
    public void saveParkingSpace_WithExistentParkingCode_ReturnErrorMessageWithStatus409(){
        testClient
                .post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-05", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");
    }

    @Test
    public void saveParkingSpace_WithInvalidEntryData_ReturnErrorMessageWithStatus422(){
        testClient
                .post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("A-05", "FREEE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");

        testClient
                .post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");

        testClient
                .post()
                .uri("/api/v1/parking-spaces")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .bodyValue(new ParkingSpaceCreateDto("", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces");
    }

    @Test
    public void getParkingSpace_WithValidPathCode_ReturnsParkingSpaceWithStatus200(){
        testClient
                .get()
                .uri("/api/v1/parking-spaces/{code}", "A-02")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(12)
                .jsonPath("code").isEqualTo("A-02")
                .jsonPath("status").isEqualTo("FREE");
    }

    @Test
    public void getParkingSpace_WithInValidPathCode_ReturnsParkingSpaceWithStatus404(){
        testClient
                .get()
                .uri("/api/v1/parking-spaces/{code}", "A-08")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient,"ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parking-spaces/A-08");
    }


}
