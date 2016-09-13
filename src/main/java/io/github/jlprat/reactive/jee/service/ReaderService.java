package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Reader;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author @jlprat
 */
@Stateless
public class ReaderService {

    @PersistenceContext
    private EntityManager em;

    public List<Reader> getReadersSync() {
        return em.createNamedQuery(Reader.ALL_READERS, Reader.class).getResultList();
    }

    @Asynchronous
    public void getReaders(CompletableFuture<List<Reader>> promise) {
        promise.complete(em.createNamedQuery(Reader.ALL_READERS, Reader.class).getResultList());
    }

    public Reader createReader(final String name, final String surname) {
        final Reader reader = new Reader(UUID.randomUUID(), name, surname);
        em.persist(reader);
        return reader;
    }

    /**
     * WARNING!!! optional is not Serializable, it can't be part of any remote session bean!
     */
    public Optional<Reader> getReader(final String id) {
        return Optional.ofNullable(em.find(Reader.class, id));
    }
}
