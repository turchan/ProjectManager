package management.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "severity_risk")
public class SeverityRisk implements Serializable
{
    private String id;
    private List<Risk> riskList = new ArrayList<>();

    public SeverityRisk() {}

    @Id
    @Column(name = "SEVERITY_RISK_ID")
    public String getId() {
        return id;
    }

    @OneToMany(mappedBy = "severityRisk", fetch = FetchType.EAGER)
    public List<Risk> getRiskList() {
        return riskList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRiskList(List<Risk> riskList) {
        this.riskList = riskList;
    }
}
