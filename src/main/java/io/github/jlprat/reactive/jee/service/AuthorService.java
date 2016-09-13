package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Author;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author @jlprat
 */
@Stateless
public class AuthorService {

    @PersistenceContext
    private EntityManager em;

    public List<Author> getAuthors() {
        return em.createNamedQuery(Author.ALL_AUTHORS, Author.class).getResultList();
    }

    @Asynchronous
    public Future<Author> createAuthor(final String name, final String surname) {
        final Author author = new Author(UUID.randomUUID(), name, surname);
        em.persist(author);
        return new AsyncResult<>(author);
    }

    public Author getAuthor(final String id) {
        return em.find(Author.class, id);
    }
}
