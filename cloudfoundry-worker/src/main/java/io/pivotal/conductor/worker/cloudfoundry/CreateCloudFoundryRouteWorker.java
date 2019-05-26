package io.pivotal.conductor.worker.cloudfoundry;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

public class CreateCloudFoundryRouteWorker implements Worker {

    static final String TASK_DEF_NAME = "create_cloud_foundry_route";

    private final CloudFoundryProperties properties;
    private final CloudFoundryRouteClient cloudFoundryRouteClient;

    public CreateCloudFoundryRouteWorker(
        CloudFoundryProperties properties,
        CloudFoundryRouteClient cloudFoundryRouteClient) {
        this.properties = properties;
        this.cloudFoundryRouteClient = cloudFoundryRouteClient;
    }

    @Override
    public String getTaskDefName() {
        return TASK_DEF_NAME;
    }

    @Override
    public TaskResult execute(Task task) {
        String projectName = (String) task.getInputData().get("projectName");
        String hostnameSuffix = (String) task.getInputData().get("hostnameSuffix");
        String spaceName = (String) task.getInputData().get("spaceName");
        Boolean dryRun = Boolean.valueOf((String) task.getInputData().get("dryRun"));
        String hostname = CloudFoundryUtil.deriveRouteHostname(projectName, hostnameSuffix);

        if (!dryRun) {
            cloudFoundryRouteClient.createRoute(spaceName, hostname, properties.getDomain());
        }

        TaskResult taskResult = new TaskResult(task);
        taskResult.setStatus(Status.COMPLETED);
        taskResult.getOutputData().put("hostname", hostname);

        return taskResult;
    }
}
