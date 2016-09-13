package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Stateless
@Path("/users/readers")
public class ReaderWs {

    private static Logger logger = Logger.getLogger(ReaderWs.class.getName());

    @Inject
    private ReaderService readerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void getAuthors(@Suspended AsyncResponse response) {
        CompletableFuture<List<Reader>> promise = new CompletableFuture<>();
        readerService.getReaders(promise);
        promise.thenApply(response::resume);
    }

    @Path("/{id}")
    @GET
    public Response getReader(@PathParam("id") final String id) {
        final Reader reader = readerService.getReader(id);
        if (reader != null) {
            return Response.ok(reader).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReader(@FormParam("name") final String name, @FormParam("surname") final String surname) {
        logger.info("name " + name);
        final Reader reader = readerService.createReader(name, surname);
        logger.info("reader " + reader);
        return Response.ok(reader).build();
    }
}
