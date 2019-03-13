package playground.spring.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class A {

    private Integer id;
    private String name;
    private Integer size;

    /**
     * Ignore the ID.
     */
    public void updateFrom(A a) {

        setName(a.getName());
        setSize(a.getSize());
    }
}
