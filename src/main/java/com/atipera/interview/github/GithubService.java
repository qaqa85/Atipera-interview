package com.atipera.interview.github;

import com.atipera.interview.github.api.GithubApi;
import com.atipera.interview.github.dtos.BranchWithCommitShaDTO;
import com.atipera.interview.github.dtos.RepositoryDTO;
import com.atipera.interview.github.dtos.RepositoryWithBranchAndShaDTO;
import com.atipera.interview.github.mappers.BranchMapper;
import com.atipera.interview.github.mappers.RepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
class GithubService {
    private final GithubApi api;

    public Flux<RepositoryWithBranchAndShaDTO> getRepositoriesWithoutFork(String user) {
        return getRepositoryDtoWithoutFork(user)
                .flatMap(repository -> getBranchesWithCommitSha(repository.owner().login(), repository.name())
                        .map(branches -> RepositoryMapper.toRepositoryWithBranchAndShaDTO(repository, branches))
                );
    }

    private Flux<RepositoryDTO> getRepositoryDtoWithoutFork(String user) {
        return api.getRepositories(user)
                .filter(repository -> !repository.fork());
    }

    private Mono<List<BranchWithCommitShaDTO>> getBranchesWithCommitSha(String ownerLogin, String repositoryName) {
        return api.getBranches(ownerLogin, repositoryName)
                .map(BranchMapper::toBranchWithCommitShaDto)
                .collectList();
    }
}
