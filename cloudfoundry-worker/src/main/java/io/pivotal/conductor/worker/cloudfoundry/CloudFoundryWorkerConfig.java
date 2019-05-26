package io.pivotal.conductor.worker.cloudfoundry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(CloudFoundryConfig.class)
@Configuration
public class CloudFoundryWorkerConfig {

    @Bean
    public CreateCloudFoundrySpaceWorker createCloudFoundrySpaceWorker() {
        return new CreateCloudFoundrySpaceWorker();
    }

    @Bean
    public CreateCloudFoundryRouteWorker createCloudFoundryRouteWorker() {
        return new CreateCloudFoundryRouteWorker();
    }

    @Bean
    public CreateMysqlDatabaseServiceWorker createMysqlDatabaseServiceWorker() {
        return new CreateMysqlDatabaseServiceWorker();
    }

    @Bean
    public CreateRabbitMqServiceWorker createRabbitMqServiceWorker() {
        return new CreateRabbitMqServiceWorker();
    }

    @Bean
    public DeleteCloudFoundrySpaceWorker deleteCloudFoundrySpaceWorker() {
        return new DeleteCloudFoundrySpaceWorker();
    }

}