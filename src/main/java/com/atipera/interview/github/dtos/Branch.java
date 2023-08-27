package com.atipera.interview.github.dtos;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@JsonPropertyOrder({"name", "commit"})
public class Branch {
  @Getter
  @Setter
  @JsonProperty("name")
  private String name;

  private String sha;

  @JsonSetter("commit")
  public String getSha(Map<String, String> commit) {
    return commit.get("sha");
  }

  @JsonSetter("sha")
  public String getSha() {
    return sha;
  }
}
