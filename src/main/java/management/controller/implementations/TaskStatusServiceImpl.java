package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.TaskStatusRepository;
import management.controller.interfaces.TaskStatusService;
import management.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("taskStatusService")
public class TaskStatusServiceImpl implements TaskStatusService
{
    private TaskStatusRepository taskStatusRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TaskStatus> findAll() {
        return Lists.newArrayList(taskStatusRepository.findAll());
    }

    @Autowired
    public void setTaskStatusRepository(TaskStatusRepository taskStatusRepository)
    {
        this.taskStatusRepository = taskStatusRepository;
    }
}
