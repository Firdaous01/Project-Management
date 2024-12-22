package ma.ac.uir.javaprojectv4.Service;


import ma.ac.uir.javaprojectv4.DAO.AssignmentRepository;
import ma.ac.uir.javaprojectv4.Entity.Assignment;
import ma.ac.uir.javaprojectv4.Entity.Project;
import ma.ac.uir.javaprojectv4.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentService {


    @Autowired
    private AssignmentRepository assignmentRepository;


    public void assignDeveloperToProject(Assignment assignment) {
        if (assignmentRepository.existsByProjectAndUser(assignment.getProject(), assignment.getUser())) {
            throw new RuntimeException("This developer is already assigned to the selected project.");
        }
        assignmentRepository.save(assignment);
    }

    // Méthode pour vérifier si un utilisateur est déjà affecté à un projet
    public boolean isUserAssignedToProject(Long userId, Long projectId) {
        return assignmentRepository.existsByProjectIdPAndUserId(projectId, userId); // Utilisation de projectId au lieu de id
    }


    public List<Project> getProjectsByDeveloper(Long userId) {
        return assignmentRepository.findProjectsByUserId(userId);
    }

    // Méthode pour trouver les utilisateurs assignés à un projet
    public List<User> findUsersAssignedToProject(Long projectId) {
        // Requête pour récupérer tous les utilisateurs affectés à ce projet
        return assignmentRepository.findUsersByProjectId(projectId);
    }
}

