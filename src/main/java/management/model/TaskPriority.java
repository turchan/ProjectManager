package management.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_priority")
public class TaskPriority implements Serializable
{
    private String id;
    private List<Task> taskList = new ArrayList<>();

    public TaskPriority() {}

    @Id
    @Column(name = "TASK_PRIORITY_ID")
    public String getId() {
        return id;
    }


    @OneToMany(mappedBy = "taskPriority", fetch = FetchType.EAGER)
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
