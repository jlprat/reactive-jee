package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Reader;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author @jlprat
 */
@Stateless
public class ReaderService {

    @PersistenceContext
    private EntityManager em;

    public List<Reader> getReaders() {
        return em.createNamedQuery(Reader.ALL_READERS, Reader.class).getResultList();
    }

    public Reader createReader(final String name, final String surname) {
        final Reader reader = new Reader(UUID.randomUUID(), name, surname);
        em.persist(reader);
        return reader;
    }

    public Reader getReader(final String id) {
        return em.find(Reader.class, id);
    }
}
