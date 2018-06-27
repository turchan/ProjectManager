package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.TaskPriorityRepository;
import management.controller.interfaces.TaskPriorityService;
import management.model.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("taskPriorityService")
public class TaskPriorityServiceImpl implements TaskPriorityService
{
    private TaskPriorityRepository taskPriorityRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TaskPriority> findAll() {
        return Lists.newArrayList(taskPriorityRepository.findAll());
    }

    @Autowired
    public void setTaskPriorityRepository(TaskPriorityRepository taskPriorityRepository)
    {
        this.taskPriorityRepository = taskPriorityRepository;
    }
}
