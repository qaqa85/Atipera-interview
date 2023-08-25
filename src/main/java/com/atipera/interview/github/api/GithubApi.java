package com.atipera.interview.github.api;

import com.atipera.interview.github.dtos.BranchDTO;
import com.atipera.interview.github.dtos.RepositoryDTO;
import reactor.core.publisher.Flux;

public interface GithubApi {
    Flux<RepositoryDTO> getRepositories(String user);

    Flux<BranchDTO> getBranches(String owner, String repoName);
}
