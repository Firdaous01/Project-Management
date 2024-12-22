package ma.ac.uir.javaprojectv4.DAO;

import ma.ac.uir.javaprojectv4.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}