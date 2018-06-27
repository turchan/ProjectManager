package management.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task implements Serializable
{
    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private DateTime dateOfStart;
    private DateTime dateOfEnd;
    private List<User> userList = new ArrayList<>();
    private Project project;

    public Task() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATUS_ID")
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIORITY_ID")
    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "DATE_OF_START")
    public DateTime getDateOfStart() {
        return dateOfStart;
    }
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "DATE_OF_END")
    public DateTime getDateOfEnd() {
        return dateOfEnd;
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "TASK_USER",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    public List<User> getUserList() {
        return userList;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID")
    public Project getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setDateOfStart(DateTime dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public void setDateOfEnd(DateTime dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

