package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.Entity.Project;

import java.util.List;

public interface IProjectService {
    void createProject(Project project);

    Project getProjectById(Long projectId);

    List<Project> getAllProjects();

    List<Project> getProjectsByCreator(String createdBy);

    void updateProject(Long id, Project project);

    void deleteProject(Long projectId);

    void deleteProjectAndRelatedData(Long projectId);

}
