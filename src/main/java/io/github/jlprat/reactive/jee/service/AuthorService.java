package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Author;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author @jlprat
 */
@Stateless
public class AuthorService {

    @PersistenceContext
    private EntityManager em;

    public List<Author> getAuthorsSync() {
        return em.createNamedQuery(Author.ALL_AUTHORS, Author.class).getResultList();
    }

    @Asynchronous
    public void getAuthors(final CompletableFuture<List<Author>> promise) {
        promise.complete(em.createNamedQuery(Author.ALL_AUTHORS, Author.class).getResultList());
    }

    @Asynchronous
    public void createAuthor(final String name, final String surname, final CompletableFuture<Author> promise) {
        final Author author = new Author(UUID.randomUUID(), name, surname);
        em.persist(author);
        promise.complete(author);
    }

    public Author getAuthor(final String id) {
        return em.find(Author.class, id);
    }
}
