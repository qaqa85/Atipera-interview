package com.atipera.interview.github;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.reactive.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.*;

@SpringBootTest
@AutoConfigureWebTestClient
class GithubControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("should return error 406 when invalid accept header")
    void shouldReturnError406WhenInvalidAcceptHeader() {
      webTestClient.get().uri("/api/v1/repositories?user=qaqa85")
          .accept(MediaType.APPLICATION_XML)
          .exchange()
          .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
          .expectBody()
          .jsonPath("$.statusCode").isEqualTo(HttpStatus.NOT_ACCEPTABLE.value())
          .jsonPath("$.message").isEqualTo("Invalid accept header");
    }

    @Test
    @DisplayName("should return error 404 when user not found")
    void shouldReturnError404WhenUserNotFound() {
        webTestClient.get().uri("/api/v1/repositories?user=qaqa8585")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
            .expectBody()
            .jsonPath("$.statusCode").isEqualTo(HttpStatus.NOT_FOUND.value())
            .jsonPath("$.message").isEqualTo("User qaqa8585 not found");
    }

    @Test
    @DisplayName("should return repository with branches")
    void shouldReturnRepositoryWithBranches() {
        webTestClient.get().uri("/api/v1/repositories?user=qaqa85")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody()
            .jsonPath("$[*].repositoryName").isNotEmpty()
            .jsonPath("$[*].ownerLogin").isNotEmpty()
            .jsonPath("$[*].branches[*].name").isNotEmpty()
            .jsonPath("$[*].branches[*].sha").isNotEmpty();
    }
}