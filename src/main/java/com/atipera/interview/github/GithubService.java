package com.atipera.interview.github;

import com.atipera.interview.github.dtos.*;
import com.atipera.interview.github.exceptions.*;
import com.atipera.interview.github.mappers.BranchMapper;
import com.atipera.interview.github.mappers.RepositoryMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Flux<RepositoryWithBranchAndShaDTO> getRepositoriesWithoutFork(String user) {
        return getRepositoryDtoWithoutFork(user)
                .flatMap(repository -> getBranchesWithCommitSha(repository.owner().login(), repository.name())
                        .map(branches -> RepositoryMapper.toRepositoryWithBranchAndShaDTO(repository, branches))
                );
    }

    private Flux<RepositoryDTO> getRepositoryDtoWithoutFork(String user) {
        return getRepositories(user)
                .filter(repository -> !repository.fork());
    }

    private Mono<List<BranchWithCommitShaDTO>> getBranchesWithCommitSha(String ownerLogin, String repositoryName) {
        return getBranches(ownerLogin, repositoryName)
                .map(BranchMapper::toBranchWithCommitShaDto)
                .collectList();
    }

    private Flux<RepositoryDTO> getRepositories(String user) {
        return githubClient.get()
            .uri("users/{user}/repos", user)
            .retrieve()
            .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND),
                response -> Mono.error(new UserNotFoundException(user)))
            .bodyToFlux(RepositoryDTO.class);
    }

    private Flux<BranchDTO> getBranches(String owner, String repoName) {
        return githubClient.get()
            .uri("repos/{owner}/{repoName}/branches", owner, repoName)
            .retrieve()
            .bodyToFlux(BranchDTO.class);
    }
}
