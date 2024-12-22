package ma.ac.uir.javaprojectv4.DAO;
import ma.ac.uir.javaprojectv4.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByUserId(Long userId);
}