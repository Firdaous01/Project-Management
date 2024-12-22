package ma.ac.uir.javaprojectv4.Service;



import ma.ac.uir.javaprojectv4.Entity.Assignment;
import ma.ac.uir.javaprojectv4.Entity.Project;
import ma.ac.uir.javaprojectv4.Entity.User;

import java.util.List;

public interface IAssignmentService {

    public void assignDeveloperToProject(Assignment assignment);

    public boolean isUserAssignedToProject(Long userId, Long projectId);

    public List<Project> getProjectsByDeveloper(Long userId);

    public List<User> findUsersAssignedToProject(Long projectId);

    public void unassignDeveloperFromProject(Assignment assignment) throws RuntimeException;


    public List<Assignment> findAllAssignments();

}

