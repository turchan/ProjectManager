package management.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "risk")
public class Risk implements Serializable
{
    private Long id;
    private String title;
    private String description;
    private SeverityRisk severityRisk;
    private Project project;

    public Risk() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RISK_ID")
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
    @JoinColumn(name = "SEVERITY_RISK_ID")
    public SeverityRisk getSeverityRisk() {
        return severityRisk;
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

    public void setSeverityRisk(SeverityRisk severityRisk) {
        this.severityRisk = severityRisk;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
