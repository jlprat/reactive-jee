package io.github.jlprat.reactive.jee.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author @jlprat
 */
@XmlRootElement
@Entity
public class Book implements Serializable {

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
