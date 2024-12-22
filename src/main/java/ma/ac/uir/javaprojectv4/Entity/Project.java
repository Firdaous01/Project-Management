package ma.ac.uir.javaprojectv4.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_p")
    private Long idP;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "required_competence", nullable = false)
    private String requiredCompetence;

    @Column(name = "estimated_time", nullable = false)
    private int estimatedTime;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    public Project() {
    }

    public Long getIdP() {
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getRequiredCompetence() {
        return requiredCompetence;
    }

    public void setRequiredCompetence(String requiredCompetence) {
        this.requiredCompetence = requiredCompetence;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Project{" +
                "idP=" + idP +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", requiredCompetence='" + requiredCompetence + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
