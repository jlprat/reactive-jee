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
@Path("/users/authors")
public class AuthorWs {

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
        final Author reader = authorService.createAuthor(name, surname);
        return Response.ok(reader).build();
    }
}
