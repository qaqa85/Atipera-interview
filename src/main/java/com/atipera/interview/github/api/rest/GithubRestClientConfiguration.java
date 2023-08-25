package com.atipera.interview.github.api.rest;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "github")
class GithubRestClientConfiguration {
    private String apiKey;
    private String apiVersion;

    @Bean
    GithubConfiguration getConfiguration() {
        if (apiKey == null || apiVersion == null) {
            throw new IllegalArgumentException("Api key & apiVersion cannot be null");
        }
        return new GithubConfiguration(apiKey, apiVersion);
    }
}
