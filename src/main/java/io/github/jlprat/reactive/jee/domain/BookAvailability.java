package io.github.jlprat.reactive.jee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import static io.github.jlprat.reactive.jee.domain.BookAvailability.ALL_BOOKS_STATUS;

/**
 * @author @jlprat
 */
@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name =ALL_BOOKS_STATUS, query = "select a from BookAvailability a")
})
public class BookAvailability {

    public static final String ALL_BOOKS_STATUS = "allBooksStatus";

    @Id
    private String isbn;

    private boolean available;


    public BookAvailability() {
    }

    public BookAvailability(String isbn) {
        this.isbn = isbn;
        this.available = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void returned() {
        available = true;
    }

    public void lend() {
        available = false;
    }
}
