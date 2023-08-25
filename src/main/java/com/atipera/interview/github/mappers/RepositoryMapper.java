package com.atipera.interview.github.mappers;

import com.atipera.interview.github.dtos.BranchWithCommitShaDTO;
import com.atipera.interview.github.dtos.RepositoryDTO;
import com.atipera.interview.github.dtos.RepositoryWithBranchAndShaDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryMapper {
    public static RepositoryWithBranchAndShaDTO toRepositoryWithBranchAndShaDTO(RepositoryDTO repository,
                                                                                List<BranchWithCommitShaDTO> branches) {
        return new RepositoryWithBranchAndShaDTO(repository.name(), repository.owner().login(), branches);
    }
}
