package ma.ac.uir.javaprojectv4.DAO;

import ma.ac.uir.javaprojectv4.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    boolean existsByProjectAndUser(Project project, User user);
    boolean existsByProjectIdPAndUserId(Long idP, Long userId);

    @Query("SELECT a.project FROM Assignment a WHERE a.user.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);

    // Requête pour récupérer tous les utilisateurs affectés à un projet donné
    @Query("SELECT u FROM User u JOIN Assignment a ON u.id = a.user.id WHERE a.project.idP = :projectId")
    List<User> findUsersByProjectId(@Param("projectId") Long projectId);
}





