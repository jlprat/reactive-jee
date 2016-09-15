package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.domain.BookAvailability;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.event.BookLoan;
import io.github.jlprat.reactive.jee.event.ReturnedBook;
import io.github.jlprat.reactive.jee.exception.BookNotAvailableException;
import io.github.jlprat.reactive.jee.exception.BookNotLentException;
import io.github.jlprat.reactive.jee.exception.ReaderNotInPossesionOfBookException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
public class LendingService {

    private static final Logger logger = Logger.getLogger(LendingService.class.getName());

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/BookLendingQueue")
    private Destination bookLendingQueue;

    @PersistenceContext
    private EntityManager em;

    @Inject
    @ReturnedBook
    Event<BookLoan> returnedBook;


    public BookAvailability lendBook(final Book book, final Reader reader) {
        final BookAvailability bookAvailability = em.find(BookAvailability.class, book.getIsbn());
        if (!bookAvailability.isAvailable()) {
            throw new BookNotAvailableException();
        } else {
            sendLoanBookJMS(reader, book);
            bookAvailability.lend();
            em.merge(bookAvailability);
            return bookAvailability;
        }
    }

    private void sendLoanBookJMS(Reader reader, Book book) {
        final Map<String, Object> message = new HashMap<>();
        message.put("readerId", reader.getId());
        message.put("bookIsbn", book.getIsbn());
        jmsContext.createProducer()
                .send(bookLendingQueue, message);
        logger.info("sent message!");
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
            if (reader.getBookShelf().contains(book)) {
                logger.info("CDI Event producer - " + Thread.currentThread().getName());
                returnedBook.fire(new BookLoan(book, reader));
                bookAvailability.returned();
                em.merge(bookAvailability);
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
