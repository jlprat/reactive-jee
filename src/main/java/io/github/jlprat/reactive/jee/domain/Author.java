package io.github.jlprat.reactive.jee.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.github.jlprat.reactive.jee.domain.Author.ALL_AUTHORS;

/**
 * @author @jlprat
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name=ALL_AUTHORS, query="select a from Author a")
})
public class Author extends Person implements Serializable {

    public static final String ALL_AUTHORS = "allAuthors";

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> booksAuthored;

    public Author() {
        super();
        booksAuthored = new ArrayList<>();
    }

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
