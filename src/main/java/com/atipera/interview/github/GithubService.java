package com.atipera.interview.github;

import com.atipera.interview.github.dtos.Repository;
import com.atipera.interview.github.dtos.*;
import com.atipera.interview.github.exceptions.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;
import reactor.core.scheduler.*;

import java.util.*;

@Service
class GithubService {
    private final WebClient githubClient;

    public GithubService(WebClient.Builder webclient, Credentials credentials) {
        githubClient = webclient.baseUrl("https://api.github.com")
            .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer" + credentials.apiKey())
            .defaultHeader("X-GitHub-Api-Version", credentials.apiVersionHeader())
            .build();
    }

    public Flux<Repository> getRepositoriesWithBranches(String user) {
        return getRepositoryDtoWithoutFork(user)
            .flatMap(repository -> getBranches(repository.getOwnerLogin(), repository.getRepositoryName())
                    .map(branches -> {
                        repository.setBranches(branches);
                        return repository;
                    }).subscribeOn(Schedulers.parallel()));
    }

    private Flux<Repository> getRepositoryDtoWithoutFork(String user) {
        return getRepositories(user)
                .filter(repository -> !repository.getFork());
    }

    private Flux<Repository> getRepositories(String user) {
        return githubClient.get()
            .uri("users/{user}/repos", user)
            .retrieve()
            .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND),
                response -> Mono.error(new UserNotFoundException(user)))
            .bodyToFlux(Repository.class);
    }

    private Mono<List<Branch>> getBranches(String owner, String repoName) {
        return githubClient.get()
            .uri("repos/{owner}/{repoName}/branches", owner, repoName)
            .retrieve()
            .bodyToFlux(Branch.class)
            .collectList();
    }
}
