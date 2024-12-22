package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.Entity.User;

public interface IUserService {
    User findByLoginAndPassword(String login, String password);
    String registerUser(User user);
    User getUserById(Long id);
}