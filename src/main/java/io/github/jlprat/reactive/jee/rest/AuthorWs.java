package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.service.AuthorService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author @jlprat
 */
@Stateless
@Path("/users/authors")
public class AuthorWs {

    @Inject
    private AuthorService authorService;

    @GET
    public Response getAuthors() {
        return Response.ok(authorService.getAuthors()).build();
    }
}
