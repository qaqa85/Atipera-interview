package com.atipera.interview.github.mappers;

import com.atipera.interview.github.dtos.BranchDTO;
import com.atipera.interview.github.dtos.BranchWithCommitShaDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchMapper {
    public static BranchWithCommitShaDTO toBranchWithCommitShaDto(BranchDTO branchDTO) {
        return new BranchWithCommitShaDTO(branchDTO.name(), branchDTO.commit().sha());
    }
}
