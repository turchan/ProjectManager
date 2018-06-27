package management.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_status")
public class TaskStatus implements Serializable
{
    private String id;
    private List<Task> taskList = new ArrayList<>();

    public TaskStatus() {}

    @Id
    @Column(name = "TASK_STATUS_ID")
    public String getId() {
        return id;
    }

    @OneToMany(mappedBy = "taskStatus",fetch = FetchType.EAGER)
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
