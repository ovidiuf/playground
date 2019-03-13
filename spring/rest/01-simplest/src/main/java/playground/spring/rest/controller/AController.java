package playground.spring.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

        System.out.println("GET /a");

        return content.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<A> get(@PathVariable("id") Integer id) {

        System.out.println("GET /a/" + id);

        A a = content.get(id);

        if (a == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public A post(@RequestBody A a) {

        System.out.println("POST /a " + a);

        content.put(a.getId(), a);

        return a;
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<A> put(@PathVariable("id") Integer id, @RequestBody A a) {

        System.out.println("PUT /a/" + id + " " + a);

        //
        // wholesale replacement, make sure there is such an ID and the A instance is valid
        //

        A old = content.get(id);

        if (old == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        old.updateFrom(a);

        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<A> patch(@PathVariable("id") Integer id, @RequestBody A a) {

        System.out.println("PATCH /a/" + id + " " + a);

        A current = content.get(id);

        if (current == null) {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (a.getName() != null) {

            current.setName(a.getName());
        }

        if (a.getSize() != null) {

            current.setSize(a.getSize());
        }

        return new ResponseEntity<>(current, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        System.out.println("DELETE /a/" + id);

        content.remove(id);
    }

}
