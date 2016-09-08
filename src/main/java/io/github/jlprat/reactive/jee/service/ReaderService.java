package io.github.jlprat.reactive.jee.service;

import io.github.jlprat.reactive.jee.domain.Reader;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author @jlprat
 */
@Stateless
public class ReaderService {


    public List<Reader> getReaders() {
        return new ArrayList<>();
    }

    public Reader createReader(final String name, final String surname) {
        final Reader reader = new Reader(UUID.randomUUID(), name, surname);

        return reader;
    }
}
