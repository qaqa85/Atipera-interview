package com.atipera.interview.github.dtos;

public record RepositoryDTO(OwnerDTO owner, String name, Boolean fork) {
}
