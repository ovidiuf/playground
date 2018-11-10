package playground.spring.sia.chapterfour.tacocloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import playground.spring.sia.chapterfour.tacocloud.persistence.UserRepository;
import playground.spring.sia.chapterfour.tacocloud.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {

        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {

        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
