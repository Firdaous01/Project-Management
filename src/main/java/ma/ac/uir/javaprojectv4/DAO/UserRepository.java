package ma.ac.uir.javaprojectv4.DAO;
import ma.ac.uir.javaprojectv4.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginAndMotDePasse(String login, String motDePasse);
    boolean existsByLogin(String login);
    boolean existsById(Long id);
    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE u.competence LIKE %:competence% AND u.experience >= :experience AND u.role = 'dev'")
    List<User> findDevelopersByCompetenceAndExperience(String competence, int experience);



    // Trouver les utilisateurs affectés à un projet spécifique
    @Query("SELECT a.user FROM Assignment a WHERE a.project.idP = :projectId")
    List<User> findUsersAssignedToProject(@Param("projectId") Long projectId);


}
