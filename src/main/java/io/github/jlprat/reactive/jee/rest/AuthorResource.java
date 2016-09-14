package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.AuthorService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author @jlprat
 */
@Stateless
@Path("/users/authors")
public class AuthorResource {

    @Inject
    private AuthorService authorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthors() {
        return Response.ok(authorService.getAuthors()).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
        final Author author = authorService.createAuthor(name, surname);
        return Response.ok(author).build();
    }
}
