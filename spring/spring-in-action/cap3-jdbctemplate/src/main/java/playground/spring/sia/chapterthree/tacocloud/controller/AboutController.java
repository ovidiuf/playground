package playground.spring.sia.chapterthree.tacocloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String home() {

        // returns the view name
        return "about";
    }
}
