package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.DAO.UserRepository;
import ma.ac.uir.javaprojectv4.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeveloperService implements IDeveloperService{

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public void updateAccount(Long userId, String competence, int experience) {
        User user = getUserById(userId);
        if (competence != null && !competence.isEmpty()) {
            user.setCompetence(competence);
        }
        if (experience >= 0) {
            user.setExperience(experience);
        }
        userRepository.save(user);
    }

    public List<User> findDevelopers(String competence, int experience) {
        return userRepository.findDevelopersByCompetenceAndExperience(competence, experience);
    }
}
