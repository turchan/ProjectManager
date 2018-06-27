package management.controller.implementations;

import com.google.common.collect.Lists;
import management.controller.interfaces.ProjectRepository;
import management.controller.interfaces.ProjectService;
import management.model.Project;
import management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("projectService")
public class ProjectServiceImpl implements ProjectService
{
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Project> findAll() {
        return Lists.newArrayList(projectRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void delete(Project project) {
        projectRepository.delete(project);
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository)
    {
        this.projectRepository = projectRepository;
    }
}
