package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.DAO.AssignmentRepository;
import ma.ac.uir.javaprojectv4.DAO.ProjectRepository;
import ma.ac.uir.javaprojectv4.Entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {


    @Autowired
    private ProjectRepository projectRepository;

    public void createProject( Project project) {
        projectRepository.save(project);
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
    }


//getAll Projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    // Update an existing project
    public void updateProject(Long id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject.setRequiredCompetence(project.getRequiredCompetence());
        existingProject.setEstimatedTime(project.getEstimatedTime());

        projectRepository.save(existingProject);  // Save the updated project to the database
    }


    // Delete a project by ID
    public boolean deleteProject(Long projectId) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        projectRepository.delete(existingProject);
        return true;  // Return true to indicate that the project was deleted
    }

}
