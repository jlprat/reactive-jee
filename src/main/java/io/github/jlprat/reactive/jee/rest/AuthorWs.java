package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.service.AuthorService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
@Path("/users/authors")
public class AuthorWs {

    private static final Logger logger = Logger.getLogger(AuthorWs.class.getName());


    @Inject
    private AuthorService authorService;

    @GET
    public Response getAuthors() {
        return Response.ok(authorService.getAuthors()).build();
    }

    @Path("/{id}")
    @GET
    public Response getAuthor(@PathParam("id") final String id) {
        final Author author = authorService.getAuthor(id);
        if (author != null) {
            return Response.ok(author).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(@FormParam("name") final String name, @FormParam("surname") final String surname) {
        final Future<Author> authorFuture = authorService.createAuthor(name, surname);
        try {
            final Author author = authorFuture.get(2, TimeUnit.SECONDS);
            return Response.ok(author).build();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.log(Level.SEVERE, "Error processing the future", e);
            return Response.serverError().build();
        }

    }
}
