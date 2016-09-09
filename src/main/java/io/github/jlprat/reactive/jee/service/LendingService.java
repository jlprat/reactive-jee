package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.domain.BookAvailability;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.exception.BookNotAvailableException;
import io.github.jlprat.reactive.jee.exception.BookNotLentException;
import io.github.jlprat.reactive.jee.exception.ReaderNotInPossesionOfBookException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
public class LendingService {

    Logger logger = Logger.getLogger(LendingService.class.getName());


    @PersistenceContext
    private EntityManager em;

    public BookAvailability lendBook(final Book book, final Reader reader) {
        final BookAvailability bookAvailability = em.find(BookAvailability.class, book.getIsbn());
        if (!bookAvailability.isAvailable()) {
            throw new BookNotAvailableException();
        } else {
            reader.loanBook(book);
            bookAvailability.lend();

            em.merge(reader);
            em.merge(bookAvailability);
            return bookAvailability;
        }
    }

    public BookAvailability publishBook(final Book book) {
        final BookAvailability bookAvailability = new BookAvailability(book.getIsbn());
        em.persist(bookAvailability);
        return bookAvailability;
    }

    public BookAvailability returnBook(final Book book, final Reader reader) {
        final BookAvailability bookAvailability = em.find(BookAvailability.class, book.getIsbn());
        if (bookAvailability.isAvailable()) {
            throw new BookNotLentException();
        } else {
            if (reader.returnBook(book)) {
                bookAvailability.returned();
                logger.info("about to save reader");
                em.merge(reader);
                logger.info("about to save book availability");
                em.merge(bookAvailability);
                logger.info("all saved");
                return bookAvailability;
            } else {
                throw new ReaderNotInPossesionOfBookException();
            }
        }
    }

    public List<BookAvailability> status() {
        return em.createNamedQuery(BookAvailability.ALL_BOOKS_STATUS, BookAvailability.class).getResultList();
    }
}
