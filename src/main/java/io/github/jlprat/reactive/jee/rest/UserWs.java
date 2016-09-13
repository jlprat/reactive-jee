package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Person;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.AuthorService;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Rest end point for users.
 * It returns all users in system, authors and readers
 * @author @jlprat
 */
@Stateless
@Path("/users")
public class UserWs {

    private static Logger logger = Logger.getLogger(UserWs.class.getName());

    @Inject
    private AuthorService authorService;

    @Inject
    private ReaderService readerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void getUsers(@Suspended AsyncResponse response) {
        logger.info("Processing request " + Thread.currentThread().getName());
        response.setTimeout(2, TimeUnit.SECONDS);
        response.setTimeoutHandler(resp -> {
            logger.info("resuming request " + Thread.currentThread().getName());
            resp.resume(Response.status(Response.Status.REQUEST_TIMEOUT).build());
        });
        final CompletableFuture<List<Author>> authors = new CompletableFuture<>();
        final CompletableFuture<List<Reader>> readers = new CompletableFuture<>();
        authorService.getAuthors(authors);
        readerService.getReaders(readers);
        authors.thenCombine(readers, (a, r) -> {
            final List<Person> list = new ArrayList<>(a.size() + r.size());
            list.addAll(a);
            list.addAll(r);
            return list;
        }).thenApply(response::resume);
    }
}
