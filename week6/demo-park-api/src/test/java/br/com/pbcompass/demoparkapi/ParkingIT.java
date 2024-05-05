package br.com.pbcompass.demoparkapi;

import br.com.pbcompass.demoparkapi.web.dto.parking.ParkingCreateDto;
import br.com.pbcompass.demoparkapi.web.dto.parking.ParkingResponseDto;
import br.com.pbcompass.demoparkapi.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void checkin_WithValidEntryData_ReturnsInvoiceWithStatus200(){
        ParkingResponseDto responseDto = testClient.post()
                .uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1213", "Tesla", "S", "White", "36228624407"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody(ParkingResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseDto).isNotNull();
        Assertions.assertThat(responseDto.getBrand()).isEqualTo("Tesla");
    }

    @Test
    public void checkin_WithRoleClient_ReturnsErrorMessageWithStatus403(){
        ErrorMessage response = testClient.post()
                .uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1213", "Tesla", "S", "White", "02797865227"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    public void checkin_WithInvalidEntryData_ReturnsErrorMessageWithStatus422(){
        ErrorMessage response = testClient.post()
                .uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("", "", "", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }

    @Test
    public void checkin_WithCPFNotRegistered_ReturnsErrorMessageWithStatus404(){
        ErrorMessage response = testClient.post()
                .uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingCreateDto("ABC-1213", "Tesla", "S", "White", "71178348113"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void findByInvoice_WithAdminProfile_ReturnsParkingResponseDtoWithStatus200(){
        ParkingResponseDto response = testClient.get()
                .uri("/api/v1/parking/check-in/{invoice}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkingResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getInvoice()).isEqualTo("20230313-101300");
        Assertions.assertThat(response.getPlate()).isEqualTo("ACB-0100");
        Assertions.assertThat(response.getBrand()).isEqualTo("FIAT");
        Assertions.assertThat(response.getModel()).isEqualTo("Uno Mille");
        Assertions.assertThat(response.getColor()).isEqualTo("Black");
        Assertions.assertThat(response.getClientCpf()).isEqualTo("36228624407");
        Assertions.assertThat(response.getParkingSpaceCode()).isEqualTo("A-02");
    }

    @Test
    public void findByInvoice_WithClientProfile_ReturnsParkingResponseDtoWithStatus200(){
        ParkingResponseDto response = testClient.get()
                .uri("/api/v1/parking/check-in/{invoice}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ParkingResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getInvoice()).isEqualTo("20230313-101300");
        Assertions.assertThat(response.getPlate()).isEqualTo("ACB-0100");
        Assertions.assertThat(response.getBrand()).isEqualTo("FIAT");
        Assertions.assertThat(response.getModel()).isEqualTo("Uno Mille");
        Assertions.assertThat(response.getColor()).isEqualTo("Black");
        Assertions.assertThat(response.getClientCpf()).isEqualTo("36228624407");
        Assertions.assertThat(response.getParkingSpaceCode()).isEqualTo("A-02");
    }

    @Test
    public void findByInvoice_WithNonexistentInvoice_ReturnsErrorMessageWithStatus404(){
        ErrorMessage response = testClient.get()
                .uri("/api/v1/parking/check-in/{invoice}", "20230313-101320")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void checkout_WithValidInvoice_ReturnsParkingResponseDtoWithStatus200(){
        testClient.put()
                .uri("/api/v1/parking/check-out/{invoice}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkingResponseDto("ACB-0100","FIAT", "Uno Mille", "Black", "36228624407", "20230313-101300", LocalDateTime.of(LocalDate.of(2023, 3, 13), LocalTime.of(10, 13, 00)), null, "A-02", null, null  ))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("ACB-0100")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("Uno Mille")
                .jsonPath("color").isEqualTo("Black")
                .jsonPath("clientCpf").isEqualTo("36228624407")
                .jsonPath("parkingSpaceCode").isEqualTo("A-02")
                .jsonPath("invoice").isEqualTo("20230313-101300")
                .jsonPath("checkin").isEqualTo("2023-03-13 10:13:00")
                .jsonPath("checkout").exists()
                .jsonPath("value").exists()
                .jsonPath("discount").exists();
    }

    @Test
    public void checkout_WithNonexistentInvoice_ReturnsErrorMessageWithStatus404(){
        testClient.put()
                .uri("/api/v1/parking/check-out/{invoice}", "20230313-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkingResponseDto("ACB-0100","FIAT", "Uno Mille", "Black", "36228624407", "20230313-000000", LocalDateTime.of(LocalDate.of(2023, 3, 13), LocalTime.of(10, 13, 00)), null, "A-02", null, null  ))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parking/check-out/20230313-000000")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void checkout_WithClientProfile_ReturnsErrorMessageWithStatus403(){
        testClient.put()
                .uri("/api/v1/parking/check-out/{invoice}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ParkingResponseDto("ACB-0100","FIAT", "Uno Mille", "Black", "36228624407", "20230313-101300", LocalDateTime.of(LocalDate.of(2023, 3, 13), LocalTime.of(10, 13, 00)), null, "A-02", null, null  ))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parking/check-out/20230313-101300")
                .jsonPath("method").isEqualTo("PUT");
    }
}
