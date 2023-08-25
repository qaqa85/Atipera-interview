package com.atipera.interview.github.api.rest;

import com.atipera.interview.github.api.GithubApi;
import com.atipera.interview.github.dtos.BranchDTO;
import com.atipera.interview.github.dtos.RepositoryDTO;
import com.atipera.interview.github.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class GithubRESTClient implements GithubApi {
    private final WebClient webClient;

    GithubRESTClient(GithubConfiguration configuration) {
        webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer" + configuration.apiKey())
                .defaultHeader("X-GitHub-Api-Version", configuration.apiVersionHeader())
                .build();
    }

    public Flux<RepositoryDTO> getRepositories(String user) {
        return webClient.get()
                .uri("users/{user}/repos", user)
                .retrieve()
                .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND),
                        response -> Mono.error(new UserNotFoundException(user)))
                .bodyToFlux(RepositoryDTO.class);
    }

    public Flux<BranchDTO> getBranches(String owner, String repoName) {
        return webClient.get()
                .uri("repos/{owner}/{repoName}/branches", owner, repoName)
                .retrieve()
                .bodyToFlux(BranchDTO.class);
    }
}
