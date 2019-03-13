package playground.spring.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import playground.spring.rest.model.A;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/a", produces = "application/json")
@CrossOrigin(origins = "*")
public class AController {

    private Map<Integer, A> content;

    public AController() {

        content = new HashMap<>();

        A a = new A(0, "blue", 5);

        content.put(a.getId(), a);

        a =  new A(1, "red", 6);

        content.put(a.getId(), a);

        a = new A(2, "green", 7);

        content.put(a.getId(), a);
    }

    @GetMapping
    public Collection<A> get() {

        return content.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<A> get(@PathVariable("id") Integer id) {

        A a = content.get(id);

        if (a == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public A post(@RequestBody A a) {

        content.put(a.getId(), a);

        return a;
    }
}
