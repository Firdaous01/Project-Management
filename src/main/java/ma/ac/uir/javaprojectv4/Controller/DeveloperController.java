package ma.ac.uir.javaprojectv4.Controller;

import ma.ac.uir.javaprojectv4.Entity.Evaluation;
import ma.ac.uir.javaprojectv4.Entity.Project;
import ma.ac.uir.javaprojectv4.Entity.User;
import ma.ac.uir.javaprojectv4.Service.AssignmentService;
import ma.ac.uir.javaprojectv4.Service.DeveloperService;
import ma.ac.uir.javaprojectv4.Service.EvaluationService;
import ma.ac.uir.javaprojectv4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@Controller
public class DeveloperController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private EvaluationService evaluationService;

    //* update account dev //*
    @GetMapping("/developer/updateAccount")
    public String updateAccountPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = developerService.getUserById(userId);
        model.addAttribute("user", user);
        return "UpdateDevAcc";
    }


    @PostMapping("/developer/updateAccount")
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
            return "redirect:/developer/home";
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            User user = developerService.getUserById(userId);
            model.addAttribute("user", user);
            return "UpdateDevAcc";
        }
    }
    //* update account dev //*

    //* dev home //*

    @GetMapping("/developer/home")
    public String developerHome() {
        return "DevHome";
    }
    //* dev home //*

    //* projects devs //*

    @GetMapping("/developer/currentProjects")
    public String showAssignedProjects(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        try {
            List<Project> projects = assignmentService.getProjectsByDeveloper(userId);
            model.addAttribute("projects", projects);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error!");
        }
        return "ConsultPjt";
    }

    //* projects devs //*


    //* projects evaluations//*
    @GetMapping("/developer/evaluations")
    public String showEvaluations(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<Evaluation> evaluations = evaluationService.getEvaluationsByDeveloper(userId);
        model.addAttribute("evaluations", evaluations);
        return "ConsultEval";
    }
    //* projects evaluations//*
}
