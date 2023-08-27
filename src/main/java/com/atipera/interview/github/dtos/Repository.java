package com.atipera.interview.github.dtos;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"repositoryName", "ownerLogin", "branches"})
public class Repository {
  private String ownerLogin;
  private String repositoryName;
  private Boolean fork;
  @Getter
  @Setter
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<Branch> branches;

  @JsonSetter("owner")
  public void setOwnerLogin(Map<String, String> owner) {
    this.ownerLogin = owner.get("login");
  }

  @JsonSetter("name")
  public void setRepositoryName(String repositoryName) {
    this.repositoryName = repositoryName;
  }

  @JsonSetter("fork")
  public void setFork(Boolean fork) {
    this.fork = fork;
  }

  @JsonGetter("ownerLogin")
  public String getOwnerLogin() {
    return ownerLogin;
  }

  @JsonGetter("repositoryName")
  public String getRepositoryName() {
    return repositoryName;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Boolean getFork() {
    return fork;
  }
}
