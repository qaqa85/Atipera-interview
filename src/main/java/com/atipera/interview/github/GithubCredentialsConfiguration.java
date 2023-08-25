package com.atipera.interview.github;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "github")
class GithubCredentialsConfiguration {
    private String apiKey;
    private String apiVersion;

    @Bean
    Credentials getConfiguration() {
        if (apiKey == null || apiVersion == null) {
            throw new IllegalArgumentException("Api key & apiVersion cannot be null");
        }
        return new Credentials(apiKey, apiVersion);
    }
}
