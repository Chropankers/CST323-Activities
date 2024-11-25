package main.java.com.example.demo.controllers;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("username", oauth2User.getAttribute("username"));
        model.addAttribute("avatarUrl", oauth2User.getAttribute("avatar"));
        model.addAttribute("discriminator", oauth2User.getAttribute("discriminator"));
        return "dashboard";
    }
}
