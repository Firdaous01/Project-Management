package ma.ac.uir.javaprojectv4.Controller;
import ma.ac.uir.javaprojectv4.Entity.User;
import ma.ac.uir.javaprojectv4.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    //* login form //*
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "Login";
    }

    
    @PostMapping("/login")
    public String handleLogin(
            @ModelAttribute("user") User user,
            Model model,
            HttpSession session) {
        System.out.println("handleLogin");
        User existingUser = userService.findByLoginAndPassword(user.getLogin(), user.getMotDePasse());
        if (existingUser != null) {
            session.setAttribute("userId", existingUser.getId());
            session.setAttribute("role", existingUser.getRole());
            System.out.println(session.getAttribute("userId"));
            session.setAttribute("nameUser", existingUser.getNom());
            if ("dev".equalsIgnoreCase(existingUser.getRole())) {
                return "redirect:/Dev";
            } else if ("chef".equalsIgnoreCase(existingUser.getRole())) {
                return "redirect:/Chef";
            } else {
                model.addAttribute("error1", "Unknown role");
                return "Login";
            }
        } else {
            model.addAttribute("error", "Invalid login or password");
            return "Login";
        }
    }
    //* login form //*

    //* direc -> dev //*

    @GetMapping("/Dev")
    public String devPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/DevHome";
        }
        model.addAttribute("userId", userId);
        return "DevHome";
    }
    //* direc -> dev //*

    //* direc -> chef //*

    @GetMapping("/Chef")
    public String chefPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userId);
        return "ChefHome";
    }
    //* direc -> chef //*

    //* register  //*

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "Registrer";

    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user, Model model) {
        String result = userService.registerUser(user);
        if (result.equals("User registered successfully!")) {
            return "redirect:/login";
        } else {
            model.addAttribute("error2", result);
            return "Registrer";
        }}
    //* register  //*

    //* logout  //*

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    //* logout  //*

   }

