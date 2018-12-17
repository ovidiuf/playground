package playground.spring.jpa.model;

import playground.spring.jpa.SpringBootJpaRepositoryApplication;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created;

    public Item() {
    }

    public Item(Date created) {

        this.created = created;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Date getCreated() {

        return created;
    }

    public void setCreated(Date created) {

        this.created = created;
    }

    @Override
    public String toString() {

        return "item created on " + SpringBootJpaRepositoryApplication.TIMESTAMP.format(created);

    }
}
