package playground.spring.boot.rest.rest2swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ovidiu Feodorov <ofeodorov@uplift.com>
 * @since 2019-02-14
 */
@RestController
@RequestMapping(path="/a", produces = "application/json")
public class AController {

    @GetMapping
    public String get() {

        return "this is A";
    }
}
