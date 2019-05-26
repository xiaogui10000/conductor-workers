package io.pivotal.conductor.worker.app;

import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator.Builder;
import com.netflix.conductor.client.worker.Worker;
import io.pivotal.conductor.worker.app.ConductorConfig.ImportCloudFoundryWorkerConfig.PropertyBindingCloudFoundryProperties;
import io.pivotal.conductor.worker.cloudfoundry.CloudFoundryProperties;
import io.pivotal.conductor.worker.cloudfoundry.CloudFoundryWorkerConfig;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(ConductorProperties.class)
@Configuration
public class ConductorConfig {

    @Autowired
    private ConductorProperties properties;

    @EnableConfigurationProperties(PropertyBindingCloudFoundryProperties.class)
    @Import(CloudFoundryWorkerConfig.class)
    @Configuration
    static class ImportCloudFoundryWorkerConfig {

        @ConfigurationProperties("portal.cloudfoundry")
        static class PropertyBindingCloudFoundryProperties extends CloudFoundryProperties {}
    }

    @Bean
    public TaskClient taskClient() {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI(properties.getConductorRootUri());
        return taskClient;
    }

    @Bean
    public WorkflowTaskCoordinator taskCoordinator(Collection<Worker> workers) {

        WorkflowTaskCoordinator taskCoordinator = new Builder()
            .withTaskClient(taskClient())
            .withWorkers(workers)
            .build();
        taskCoordinator.init();
        return taskCoordinator;
    }

}