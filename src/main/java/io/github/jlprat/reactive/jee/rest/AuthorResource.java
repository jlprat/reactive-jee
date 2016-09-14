package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.service.AuthorService;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
@Path("/users/authors")
public class AuthorResource {

    private static final Logger logger = Logger.getLogger(AuthorResource.class.getName());


    @Inject
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void getAuthors(@Suspended AsyncResponse response) {
        CompletableFuture<List<Author>> promise = new CompletableFuture<>();
        authorService.getAuthors(promise);
        promise.thenApply(response::resume);
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("id") final String id) {
        final Optional<Author> author = authorService.getAuthor(id);
        return author
                .map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void createAuthor(@FormParam("name") final String name, @FormParam("surname") final String surname,
                             @Suspended AsyncResponse response) {
        CompletableFuture<Author> futureAuthor = new CompletableFuture<>();
        authorService.createAuthor(name, surname, futureAuthor);
        futureAuthor.thenApply(response::resume);
    }
}
