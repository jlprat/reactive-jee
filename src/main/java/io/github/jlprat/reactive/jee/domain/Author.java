package io.github.jlprat.reactive.jee.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author @jlprat
 */
@Entity
@XmlRootElement
public class Author extends Person implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Book> booksAuthored;

    public Author(UUID id, String name, String surname) {
        super(id, name, surname);
        booksAuthored = new ArrayList<>();
    }

    public List<Book> getBooksAuthored() {
        return booksAuthored;
    }

    public List<Book> writtenBook(final Book book) {
        booksAuthored.add(book);
        return booksAuthored;
    }


}
