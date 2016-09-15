package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Book;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Asynchronous
    public Future<List<Book>> getBooks() {
        return new AsyncResult<>(em.createNamedQuery(Book.ALL_BOOKS, Book.class).getResultList());
    }

    public Optional<Book> getBook(final String isbn) {
        return Optional.ofNullable(em.find(Book.class, isbn));
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
