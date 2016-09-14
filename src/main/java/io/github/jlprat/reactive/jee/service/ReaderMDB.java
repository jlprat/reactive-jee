package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.domain.Reader;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by @jlprat
 */

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/BookLendingQueue")
})
@Stateless
public class ReaderMDB implements MessageListener {

    private static final Logger logger = Logger.getLogger(ReaderMDB.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        logger.info("received message!");
        final MapMessage msg = (MapMessage) message;
        try {
            Reader reader = em.find(Reader.class, msg.getString("readerId"));
            Book book = em.find(Book.class, msg.getString("bookIsbn"));
            reader.loanBook(book);
            em.merge(reader);
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "Error processing Message", e);
        }
    }
}
