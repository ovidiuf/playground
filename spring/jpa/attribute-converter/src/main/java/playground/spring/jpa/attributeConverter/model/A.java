package playground.spring.jpa.attributeConverter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import playground.spring.jpa.attributeConverter.PayloadConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class A {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Convert(converter = PayloadConverter.class)
    private String payload;

}
