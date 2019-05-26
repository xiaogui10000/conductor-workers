package io.pivotal.conductor.worker.github;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GitHubConfig {

    @Bean
    public RestOperations gitHubRestOperations() {
        return new RestTemplate();
    }

}