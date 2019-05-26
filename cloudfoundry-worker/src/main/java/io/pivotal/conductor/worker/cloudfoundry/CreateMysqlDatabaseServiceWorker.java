package io.pivotal.conductor.worker.cloudfoundry;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

public class CreateMysqlDatabaseServiceWorker implements Worker {

    static final String TASK_DEF_NAME = "create_mysql_database";

    private final CloudFoundryServiceClient cloudFoundryServiceClient;

    public CreateMysqlDatabaseServiceWorker(CloudFoundryServiceClient cloudFoundryServiceClient) {
        this.cloudFoundryServiceClient = cloudFoundryServiceClient;
    }

    @Override
    public String getTaskDefName() {
        return TASK_DEF_NAME;
    }

    @Override
    public TaskResult execute(Task task) {
        String projectName = (String) task.getInputData().get("projectName");
        String spaceNameSuffix = (String) task.getInputData().get("spaceNameSuffix");
        String spaceName = (String) task.getInputData().get("spaceName");
        Boolean dryRun = Boolean.valueOf((String) task.getInputData().get("dryRun"));
        String databaseName = CloudFoundryUtil.deriveDatabaseName(projectName, spaceNameSuffix);

        if (!dryRun) {
            cloudFoundryServiceClient.createMysqlDatabase(databaseName, spaceName);
        }

        TaskResult taskResult = new TaskResult(task);
        taskResult.setStatus(Status.COMPLETED);
        taskResult.getOutputData().put("databaseName", databaseName);

        return taskResult;
    }
}
