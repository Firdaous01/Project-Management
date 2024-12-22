package ma.ac.uir.javaprojectv4.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Evaluation")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_e")
    private Long idE;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @Column(name = "note", nullable = false)
    private int note;

    @Column(name = "feedback", length = 1000)
    private String feedback;

    public Evaluation() {
    }

    public Evaluation(Long idE, User user, Project project, int note, String feedback) {
        this.idE = idE;
        this.user = user;
        this.project = project;
        this.note = note;
        this.feedback = feedback;
    }

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getNote() {
        return note;
    }


    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "idE=" + idE +
                ", user=" + user +
                ", project=" + project +
                ", note=" + note +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
