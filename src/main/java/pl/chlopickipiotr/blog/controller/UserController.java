package pl.chlopickipiotr.blog.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.chlopickipiotr.blog.dto.SignUpForm;
import pl.chlopickipiotr.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String getSignUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "signupForm";
    }

    @PostMapping(path = "/signup-submit")
    public String submitSignUpForm(@ModelAttribute SignUpForm signUpForm, Model model) {
        log.info("signUpForm {}", signUpForm);
        model.addAttribute("signUpForm", new SignUpForm());
        userService.registerUser(signUpForm);
        return "signupSuccess";
    }

    @GetMapping(path = "/login-form")
    public String getLoginPage() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logoutPage(final HttpServletRequest request, final HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/login-form";
    }

}
