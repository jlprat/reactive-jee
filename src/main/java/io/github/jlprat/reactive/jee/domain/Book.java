package io.github.jlprat.reactive.jee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static io.github.jlprat.reactive.jee.domain.Book.ALL_BOOKS;

/**
 * @author @jlprat
 */
@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name=ALL_BOOKS, query = "select b from Book b")
})
public class Book implements Serializable {

    public static final String ALL_BOOKS = "allBooks";

    @Id
    private String isbn;
    private String title;
    private String authorName;
    private int pages;


    public Book() {
    }

    public Book(String isbn, String title, String authorName, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + authorName +
                ", pages=" + pages +
                '}';
    }
}
