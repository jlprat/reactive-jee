package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Person;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.AuthorService;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest end point for users.
 * It returns all users in system, authors and readers
 * @author @jlprat
 */
@Stateless
@Path("/users")
public class UserWs {

    @Inject
    private AuthorService authorService;

    @Inject
    private ReaderService readerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        final List<Author> authors = authorService.getAuthors();
        final List<Reader> readers = readerService.getReaders();
        final List<Person> users = new ArrayList<>(authors.size() + readers.size());
        users.addAll(authors);
        users.addAll(readers);
        return Response.ok(users).build();
    }
}
