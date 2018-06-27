package management.controller.interfaces;

import management.model.TaskStatus;

import java.util.List;

public interface TaskStatusService
{
    List<TaskStatus> findAll();
}
