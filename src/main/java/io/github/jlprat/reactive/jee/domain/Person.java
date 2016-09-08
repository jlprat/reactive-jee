package io.github.jlprat.reactive.jee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author @jlprat
 */
@XmlRootElement
@Entity
public class Person implements Serializable {

    @Id
    protected final String id;
    protected final String name;
    protected final String surname;


    public Person(UUID id, String name, String surname) {
        this.id = id.toString();
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
