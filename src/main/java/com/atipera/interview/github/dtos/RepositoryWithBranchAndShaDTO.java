package com.atipera.interview.github.dtos;

import java.util.List;

public record RepositoryWithBranchAndShaDTO(String repository, String owner, List<BranchWithCommitShaDTO> branches) {
}
