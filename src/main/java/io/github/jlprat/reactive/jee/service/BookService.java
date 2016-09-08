package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jpra
 *         copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
@Stateless
public class BookService {

    public List<Book> getBooks() {
        return new ArrayList<>();
    }

    public Book getBook(final String isbn) {
        return null;
    }
}
