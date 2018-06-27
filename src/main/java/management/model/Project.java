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
@Table(name = "project")
public class Project implements Serializable
{
    private Long id;
    private String title;
    private String customer;
    private String description;
    private DateTime dateOfStart;
    private DateTime dateOfEnd;
    private Set<Risk> riskSet = new HashSet<>();
    private List<Task> taskList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Bug> bugList = new ArrayList<>();

    public Project() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    @Column(name = "CUSTOMER")
    public String getCustomer() {
        return customer;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @Column(name = "DATE_OF_START")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getDateOfStart() {
        return dateOfStart;
    }

    @Column(name = "DATE_OF_END")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getDateOfEnd() {
        return dateOfEnd;
    }

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Risk> getRiskSet() {
        return riskSet;
    }

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Task> getTaskList() {
        return taskList;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_PROJECT",
            joinColumns = @JoinColumn(name = "PROJECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    public List<User> getUserList() {
        return userList;
    }

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Bug> getBugList() {
        return bugList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOfStart(DateTime dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public void setDateOfEnd(DateTime dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public void setRiskSet(Set<Risk> riskSet) {
        this.riskSet = riskSet;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setBugList(List<Bug> bugList) {
        this.bugList = bugList;
    }
}
