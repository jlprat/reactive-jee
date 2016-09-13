package io.github.jlprat.reactive.jee.event;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.domain.Reader;

/**
 * Created by @jlprat
 */
public class BookLoan {

    public BookLoan(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
    }

    public Book book;
    public Reader reader;
}
