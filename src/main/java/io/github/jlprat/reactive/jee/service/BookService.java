package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @PersistenceContext
    private EntityManager em;

    public List<Book> getBooks() {
        return em.createNamedQuery(Book.ALL_BOOKS, Book.class).getResultList();
    }

    public Book getBook(final String isbn) {
        return em.find(Book.class, isbn);
    }

    public Book writeBook(final Author author, final String title, final int pages) {
        final Book book = new Book(UUID.randomUUID().toString(), title, author.getName(), pages);
        em.persist(book);
        logger.info("book persisted, adding to author list");
        author.writtenBook(book);
        em.merge(author);
        return book;
    }
}
