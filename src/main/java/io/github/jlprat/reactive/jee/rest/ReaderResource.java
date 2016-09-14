package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author @jlprat
 */
@Path("/users/readers")
public class ReaderResource {

    Logger logger = Logger.getLogger(ReaderResource.class.getName());

    @Inject
    private ReaderService readerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReaders() {
        return Response.ok(readerService.getReaders()).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
