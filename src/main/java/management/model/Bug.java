package management.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bug")
public class Bug implements Serializable
{
    private Long id;
    private String title;
    private String description;
    private DateTime date;
    private String summary;
    private String stepToReproduce;
    private byte[] image;
    private Project project;

    public Bug() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUG_ID")
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

    @Column(name = "DATE_SUBMITED")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public DateTime getDate() {
        return date;
    }

    @Column(name = "SUMMARY")
    public String getSummary() {
        return summary;
    }

    @Column(name = "STEP_TO_REPRODUCE")
    public String getStepToReproduce() {
        return stepToReproduce;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "IMAGE")
    public byte[] getImage() {
        return image;
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

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStepToReproduce(String stepToReproduce) {
        this.stepToReproduce = stepToReproduce;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
