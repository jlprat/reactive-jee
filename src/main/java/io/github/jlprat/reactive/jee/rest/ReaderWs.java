package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Path("/users/readers")
public class ReaderWs {

    Logger logger = Logger.getLogger(ReaderWs.class.getName());

    @Inject
    private ReaderService readerService;

    @GET
    public Response getReaders() {
        return Response.ok(readerService.getReaders()).build();
    }

    @Path("/{id}")
    @GET
    public Response getReader(@PathParam("id") final String id) {
        final Reader reader = new Reader(UUID.randomUUID(), "John", "Doe");
        return Response.ok(reader).build();
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
