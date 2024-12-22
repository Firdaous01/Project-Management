package ma.ac.uir.javaprojectv4.Controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.javaprojectv4.Entity.Assignment;
import ma.ac.uir.javaprojectv4.Entity.Evaluation;
import ma.ac.uir.javaprojectv4.Entity.Project;
import ma.ac.uir.javaprojectv4.Entity.User;
import ma.ac.uir.javaprojectv4.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProjectChefController {
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DeveloperService developerService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;

    //* home //*

    @GetMapping("/chef/home")
    public String ChefHome() {
        return "ChefHome";
    }

    //* home //*

    //* update account Chef //*
    @GetMapping("/chef/updateAccount")
    public String updateAccountPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = developerService.getUserById(userId);
        model.addAttribute("user", user);
        return "UpdateChefAcc";
    }


    @PostMapping("/chef/updateAccount")
    public String handleUpdateAccount(
            HttpSession session,
            String competence,
            int experience,
            Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            developerService.updateAccount(userId, competence, experience);
            session.invalidate();
            return "redirect:/login";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            User user = developerService.getUserById(userId);
            model.addAttribute("user", user);
            return "UpdateChefAcc";
        }
    }
    //* update account Chef //*


    //* Create Project for chef //*
    @GetMapping("/chef/create")
    public String showCreateProjectPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        String nameUser=(String) session.getAttribute("nameUser");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("project", new Project());
        return "CreatePjt";
    }


    @PostMapping("/chef/create")
    public String handleCreateProjectForm(
            @ModelAttribute("project") Project project,
            HttpSession session,
            Model model) {
        String nameUser=(String) session.getAttribute("nameUser");
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        project.setCreatedBy(nameUser);
        System.out.println(project);
        try {
            projectService.createProject(project);
            return "redirect:/chef/home";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "CreatePjt"; // Stay on the same page with error message
        }
    }
    //* Create Project for chef //*

    //* Assign a project to dev//*

@GetMapping("/chef/assignDeveloper")
public String showAssignDeveloperPage(@RequestParam(value = "projectId", required = false) Long projectId,
                                      HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return "redirect:/login";
    }
    List<Project> projects = projectService.getAllProjects();
    List<User> developers;
    if (projectId != null) {
        // Récupérer les utilisateurs non affectés à ce projet spécifique
        developers = userService.getUsersNotAssignedToProject(projectId);
    } else {
        // Si aucun projet n'est sélectionné, retourner tous les développeurs
        developers = userService.getAllDevelopers();
    }

    // Ajouter les projets et développeurs à la vue
    model.addAttribute("projects", projects);
    model.addAttribute("users", developers);
    model.addAttribute("assignment", new Assignment());

    return "AssignDev";
}

    @PostMapping("/chef/assignDeveloper")
    public String handleAssignDeveloperForm(
            @ModelAttribute("assignment") Assignment assignment,
            Model model) {
        try {
            // Assigner le développeur au projet
            assignmentService.assignDeveloperToProject(assignment);
            // Rediriger vers la page d'accueil du chef
            return "redirect:/chef/home";
        } catch (RuntimeException ex) {
            // En cas d'erreur (par exemple, une exception lancée dans assignmentService), renvoyer les données nécessaires à la vue
            model.addAttribute("errorMessage", ex.getMessage());
            List<Project> projects = projectService.getAllProjects();
            List<User> developers = userService.getUsersNotAssignedToProject(assignment.getProject().getIdP());
            model.addAttribute("projects", projects);
            model.addAttribute("users", developers);
            return "AssignDev";
        }
    }
    //* Assign a project to dev//*

    //* find dev by comptetences//*

    @GetMapping("/chef/findDeveloper")
    public String showFindDeveloperPage(Model model) {
        model.addAttribute("developers", null);
        return "FindDev";
    }


    @PostMapping("/chef/findDeveloper")
    public String handleFindDeveloper(
            @RequestParam("competence") String competence,
            @RequestParam("experience") int experience,
            Model model) {
        try {
            List<User> developers = developerService.findDevelopers(competence, experience);
            if (developers.isEmpty()) {
                model.addAttribute("errorMessage", "No developers found matching the criteria.");
            } else {
                model.addAttribute("developers", developers);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while searching for developers: " + e.getMessage());
        }
        return "FindDev";
    }
    //* find dev by comptetences//*

    //* evaluate dev //*

    @GetMapping("/chef/evaluateDeveloper")
    public String showEvaluateDeveloperPage(Model model) {
        try {
            List<User> developers = userService.getAllDevelopers(); // Method to fetch only developers
            List<Project> projects = projectService.getAllProjects();
            model.addAttribute("users", developers); // Pass developers to the view
            model.addAttribute("projects", projects);
            model.addAttribute("evaluation", new Evaluation()); // Bind an empty Evaluation object
            return "EvaluateDev"; // Render the HTML page
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading data: " + e.getMessage());
            return "EvaluateDev";
        }}


    @PostMapping("/chef/evaluateDeveloper")
    public String handleEvaluateDeveloper(
            @ModelAttribute("evaluation") Evaluation evaluation,
            @RequestParam("projectId") Long projectId,
            @RequestParam("userId") Long userId,
            Model model) {
        try {
            Project project = projectService.getProjectById(projectId);
            User user = userService.getUserById(userId);
            if (project == null || user == null) {
                model.addAttribute("errorMessage", "Invalid user or project");
                return "EvaluateDev";
            }
            evaluation.setProject(project);
            evaluation.setUser(user);
            evaluationService.saveEvaluation(evaluation);
            model.addAttribute("successMessage", "Successfully!");
            return "EvaluateDev";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving evaluation: " + e.getMessage());
            return "EvaluateDev";
        }
    }
    //* evaluate dev //*

    //* CRUD project //*
    // Method to display the list of all projects with actions
    @GetMapping("/chef/ListProject")
    public String listProjects(Model model) {
        try {
            List<Project> projects = projectService.getAllProjects();  // Fetch all projects
            model.addAttribute("projects", projects);  // Add them to the model
            return "ListProject";  // Return the view to render the list
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", "Error retrieving projects: " + ex.getMessage());
            return "ChefHome";  // Return to home page if there's an error
        }
    }


    // Method to show the update project page
    @GetMapping("/chef/updateProject/{idP}")
    public String showUpdateProjectPage(@PathVariable Long idP, Model model) {
        try {
            Project project = projectService.getProjectById(idP);
            model.addAttribute("project", project);  // Ensure this is added correctly
            return "UpdatePjt";  // Return the page to update the project
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "ChefHome";  // If an error occurs, go back to the home page
        }
    }

    // Method to handle project updates

    @PostMapping("/chef/updateProject/{idP}")
    public String handleUpdateProject(@PathVariable Long idP, @ModelAttribute("project") Project project, Model model) {
        try {
            projectService.updateProject(idP, project);  // Ensure this updates the project in your database
            return "redirect:/chef/ListProject";  // Redirect to the ListProject page after update
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", "Error updating project: " + ex.getMessage());
            return "UpdatePjt";  // Return to the update page if there's an error
        }
    }

    // Method to delete a project
    @GetMapping("/chef/deleteProject/{idP}")
    public String handleDeleteProject(@PathVariable Long idP, Model model) {
        try {
            projectService.deleteProject(idP);  // Delete the project
            return "redirect:/chef/ListProject";  // Redirect to ListProject page after deletion
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", "Error deleting project: " + ex.getMessage());
            return "ChefHome";  // If there's an error, go back to ListProject page
        }
    }
    //* CRUD project //*

}

