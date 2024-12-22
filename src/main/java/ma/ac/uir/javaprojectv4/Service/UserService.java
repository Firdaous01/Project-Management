package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.DAO.UserRepository;
import ma.ac.uir.javaprojectv4.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndMotDePasse(login, password);
    }


    public String registerUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            return "Login already exists. Please choose another one.";
        }
        if (!user.getMotDePasse().equals(user.getConfirmePassword())) {
            return "Passwords do not match.";
        }
        userRepository.save(user);
        return "User registered successfully!";
    }


    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }



    public List<User> getAllDevelopers() {
        return userRepository.findByRole("dev");
    }


    public List<User> getUsersNotAssignedToProject(Long projectId) {
        // Récupérer tous les utilisateurs ayant le rôle de développeur et qui ne sont pas affectés à ce projet
        List<User> allUsers = userRepository.findByRole("dev");
        List<User> assignedUsers = userRepository.findUsersAssignedToProject(projectId);

        // Filtrer les développeurs déjà affectés à ce projet
        return allUsers.stream()
                .filter(user -> !assignedUsers.contains(user))
                .collect(Collectors.toList());
    }





}

