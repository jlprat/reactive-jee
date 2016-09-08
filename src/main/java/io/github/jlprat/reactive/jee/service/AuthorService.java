package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @jlprat
 */
@Stateless
public class AuthorService {

    public List<Author> getAuthors() {
        return new ArrayList<>();
    }
}
