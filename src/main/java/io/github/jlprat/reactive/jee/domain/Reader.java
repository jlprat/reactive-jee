package io.github.jlprat.reactive.jee.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.github.jlprat.reactive.jee.domain.Reader.ALL_READERS;

/**
 * @author @jlprat
 */
@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name=ALL_READERS, query="select r from Reader r")
})
public class Reader extends Person implements Serializable {

    public static final String ALL_READERS = "allReaders";


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> bookShelf;

    public Reader() {
        super();
        bookShelf = new ArrayList<>();
    }

    public Reader(UUID id, String name, String surname) {
        super(id, name, surname);
        bookShelf = new ArrayList<>();
    }

    public List<Book> getBookShelf() {
        return bookShelf;
    }

    public List<Book> loanBook(final Book book) {
        bookShelf.add(book);
        return new ArrayList<>(bookShelf);
    }

    public boolean returnBook(final Book book) {
        return bookShelf.remove(book);
    }


    @Override
    public String toString() {
        return "Reader{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

}
