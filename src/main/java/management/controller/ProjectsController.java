package management.controller;

import management.controller.interfaces.*;
import management.controller.util.UrlUtil;
import management.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/projects")
public class ProjectsController
{
    private ProjectService projectService;
    private UserService userService;
    private RiskService riskService;
    private TaskService taskService;
    private RoleService roleService;
    private BugService bugService;
    private TaskStatusService taskStatusService;
    private TaskPriorityService taskPriorityService;
    private SeverityRiskService severityRiskService;
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByEmail(name);

        List<Project> projectList = user.getProjectList();

        uiModel.addAttribute("projectList", projectList);

        return "projects/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(id);

        uiModel.addAttribute("project", project);

        return "projects/show";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Project project, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_create_fail", new Object[]{}, locale)));

            uiModel.addAttribute("project", project);

            return "projects/create";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_create_success", new Object[]{}, locale)));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userService.findByEmail(name);

        Role role = roleService.findAll().get(0);
        user.setRole(role);

        project.getUserList().add(user);

        projectService.save(project);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/create", params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel)
    {
        Project project = new Project();

        uiModel.addAttribute("project", project);

        return "projects/create";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(Project project, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_save_fail", new Object[]{}, locale)));

            uiModel.addAttribute("project", project);

            return "projects/update";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_save_success", new Object[]{}, locale)));

        projectService.save(project);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel)
    {
        uiModel.addAttribute("project", projectService.findById(id));

        return "projects/update";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/deleteUser/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        User user = userService.findById(id);

        project.getUserList().remove(user);
        projectService.save(project);

        uiModel.addAttribute("project", project);

        return "projects/show";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/userSearch", method = RequestMethod.GET)
    public String searchUser(@PathVariable("projectId") Long projectId,
                             @RequestParam(value = "email", required = false) String email,
                             Model uiModel)
    {
        Project project = projectService.findById(projectId);
        List<User> userList = userService.findAllByEmail(email);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("userList", userList);

        return "projects/searchUser";
    }

    @RequestMapping(value = "/{projectId}/userAdd/{id}", method = RequestMethod.GET)
    public String addUser(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        User user = userService.findById(id);

        project.getUserList().add(user);
        projectService.save(project);

        uiModel.addAttribute("project", project);

        return "projects/searchUser";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/riskAdd", method = RequestMethod.POST)
    public String addRisk(@PathVariable("projectId") Long projectId, Risk risk,
                          Model uiModel, HttpServletRequest httpServletRequest,
                          RedirectAttributes redirectAttributes, Locale locale)
    {

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_risk_create_success", new Object[]{}, locale)));

        Project project = projectService.findById(projectId);
        riskService.save(risk);

        project.getRiskSet().add(risk);
        projectService.save(project);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/riskAdd", params = "form", method = RequestMethod.GET)
    public String addRiskForm(@PathVariable("projectId") Long projectId, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Risk risk = new Risk();
        List<SeverityRisk> severityRiskList = severityRiskService.findAll();

        uiModel.addAttribute("risk", risk);
        uiModel.addAttribute("project", project);
        uiModel.addAttribute("severityRisk", severityRiskList);

        return "projects/riskAdd";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/taskAdd", method = RequestMethod.POST)
    public String taskCreate(@PathVariable("projectId") Long projectId, Task task, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_task_create_fail", new Object[]{}, locale)));

            uiModel.addAttribute("task", task);

            return "projects/taskAdd";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_task_create_success", new Object[]{}, locale)));

        Project project = projectService.findById(projectId);
        taskService.save(task);

        project.getTaskList().add(task);
        projectService.save(project);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/taskAdd", params = "form", method = RequestMethod.GET)
    public String addTaskForm(@PathVariable("projectId") Long projectId, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = new Task();
        List<TaskStatus> taskStatusList = taskStatusService.findAll();
        List<TaskPriority> taskPriorityList = taskPriorityService.findAll();

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);
        uiModel.addAttribute("taskStatus", taskStatusList);
        uiModel.addAttribute("taskPriority", taskPriorityList);

        return "projects/taskAdd";
    }

    @RequestMapping(value = "/{projectId}/task/{id}", method = RequestMethod.GET)
    public String taskShow(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = taskService.findById(id);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);

        return "projects/taskShow";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/{id}", method = RequestMethod.POST)
    public String taskUpdate(@PathVariable("projectId") Long projectId,
                              Task task, RedirectAttributes redirectAttributes,
                              BindingResult bindingResult, Model uiModel,
                              HttpServletRequest httpServletRequest, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_task_update_fail", new Object[]{}, locale)));

            uiModel.addAttribute("task", task);

            return "projects/taskUpdate";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_task_update_success", new Object[]{}, locale)));

        Project project = projectService.findById(projectId);
        taskService.save(task);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest)
                + "/task/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/{id}", params = "form", method = RequestMethod.GET)
    public String taskUpdateForm(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = taskService.findById(id);
        List<TaskStatus> taskStatusList = taskStatusService.findAll();
        List<TaskPriority> taskPriorityList = taskPriorityService.findAll();

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);
        uiModel.addAttribute("taskStatus", taskStatusList);
        uiModel.addAttribute("taskPriority", taskPriorityList);

        return "projects/taskUpdate";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/{id}/userSearch", method = RequestMethod.GET)
    public String taskSearchUser(@PathVariable("projectId") Long projectId,
                                 @PathVariable Long id,
                                 @RequestParam(value = "email", required = false) String email,
                                 Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = taskService.findById(id);
        List<User> userList = userService.findAllByEmail(email);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);
        uiModel.addAttribute("userList", userList);

        return "projects/taskSearchUser";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/{taskId}/userAdd/{id}", method = RequestMethod.GET)
    public String taskAddUser(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId,
                              @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = taskService.findById(taskId);
        User user = userService.findById(id);

        task.getUserList().add(user);
        taskService.save(task);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);

        return "projects/taskSearchUser";
    }

    @PreAuthorize("hasRole('ROLE_TEAM_LEAD')")
    @RequestMapping(value = "/{projectId}/{taskId}/deleteUser/{id}", method = RequestMethod.GET)
    public String taskDeleteUser(@PathVariable("projectId") Long projectId,
                                 @PathVariable("taskId") Long taskId,
                                 @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Task task = taskService.findById(taskId);
        User user = userService.findById(id);

        task.getUserList().remove(user);
        taskService.save(task);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("task", task);

        return "projects/taskUpdate";
    }

    @RequestMapping(value = "/{projectId}/bugCreate", method = RequestMethod.POST)
    public String bugCreate(@PathVariable("projectId") Long projectId, Bug bug, BindingResult bindingResult,
                            Model uiModel, HttpServletRequest httpServletRequest,
                            RedirectAttributes redirectAttributes, Locale locale,
                            @RequestParam(value = "file", required = false) Part file)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_bug_create_fail", new Object[]{}, locale)));

            uiModel.addAttribute("byg", bug);

            return "projects/addBug";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_bug_create_success", new Object[]{}, locale)));

        Project project = projectService.findById(projectId);

        if (file != null)
        {
            byte[] fileContent = null;
            try
            {
                InputStream inputStream = file.getInputStream();

                fileContent = IOUtils.toByteArray(inputStream);

                bug.setImage(fileContent);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            bug.setImage(fileContent);
        }

        bugService.save(bug);

        project.getBugList().add(bug);
        projectService.save(project);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{projectId}/bugCreate", params = "form", method = RequestMethod.GET)
    public String bugCreate(@PathVariable("projectId") Long projectId, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Bug bug = new Bug();

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("bug", bug);

        return "projects/addBug";
    }

    @RequestMapping(value = "/{projectId}/bug/{id}", method = RequestMethod.GET)
    public String bugShow(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Bug bug = bugService.findById(id);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("bug", bug);

        return "projects/showBug";
    }

    @RequestMapping(value = "/{projectId}/bug/{id}", method = RequestMethod.POST)
    public String bugUpdate(@PathVariable("projectId") Long projectId,
                             Bug bug, RedirectAttributes redirectAttributes,
                             BindingResult bindingResult, Model uiModel,
                             HttpServletRequest httpServletRequest, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("project_bug_update_fail", new Object[]{}, locale)));

            uiModel.addAttribute("bug", bug);

            return "projects/bugUpdate";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("project_bug_update_success", new Object[]{}, locale)));

        Project project = projectService.findById(projectId);
        bugService.save(bug);

        return "redirect:/projects/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest)
        + "/bug/" + UrlUtil.encodeUrlPathSegment(project.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{projectId}/bug/{id}", params = "form", method = RequestMethod.GET)
    public String bugUpdateForm(@PathVariable("projectId") Long projectId, @PathVariable Long id, Model uiModel)
    {
        Project project = projectService.findById(projectId);
        Bug bug = bugService.findById(id);

        uiModel.addAttribute("project", project);
        uiModel.addAttribute("bug", bug);

        return "projects/bugUpdate";
    }

    @RequestMapping(value = "/{projectId}/bug/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("projectId")Long projectId, @PathVariable("id") Long id)
    {
        Project project = projectService.findById(projectId);
        Bug contact = bugService.findById(id);

        return contact.getImage();
    }

    @Autowired
    public void setProjectService(ProjectService projectService)
    {
        this.projectService = projectService;
    }

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setRiskService(RiskService riskService) { this.riskService = riskService; }

    @Autowired
    public void setTaskService(TaskService taskService) { this.taskService = taskService; }

    @Autowired
    public void setRoleService(RoleService roleService) { this.roleService = roleService; }

    @Autowired
    public void setBugService(BugService bugService) { this.bugService = bugService; }

    @Autowired
    public void setTaskStatusService(TaskStatusService taskStatusService) { this.taskStatusService = taskStatusService; }

    @Autowired
    public void setTaskPriorityService(TaskPriorityService taskPriorityService) { this.taskPriorityService = taskPriorityService; }

    @Autowired
    public void setSeverityRiskService(SeverityRiskService severityRiskService) { this.severityRiskService = severityRiskService; }

    @Autowired
    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }
}
