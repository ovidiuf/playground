package playground.smoke.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.smoke.model.Status;

@RestController
public class StatusController {

    @GetMapping("/status")
    public Status status() {
        return new Status();
    }
}
