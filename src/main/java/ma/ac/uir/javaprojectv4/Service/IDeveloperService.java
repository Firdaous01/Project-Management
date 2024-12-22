package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.Entity.User;

import java.util.List;

public interface IDeveloperService {
    User getUserById(Long userId);
    void updateAccount(Long userId, String competence, int experience);
    List<User> findDevelopers(String competence, int experience);
}
