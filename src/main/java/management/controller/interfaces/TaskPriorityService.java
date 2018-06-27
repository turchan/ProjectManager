package management.controller.interfaces;

import management.model.TaskPriority;

import java.util.List;

public interface TaskPriorityService
{
    List<TaskPriority> findAll();
}
