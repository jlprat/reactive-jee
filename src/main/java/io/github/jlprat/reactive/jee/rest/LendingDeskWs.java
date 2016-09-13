package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.domain.BookAvailability;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.BookService;
import io.github.jlprat.reactive.jee.service.LendingService;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author jpra
 *         copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
@Stateless
@Path("/lending")
public class LendingDeskWs {

    @Inject
    private ReaderService readerService;
    @Inject
    private BookService bookService;
    @Inject
    private LendingService lendingService;

    @POST
    @Path("/loan")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response lendBook(@FormParam("isbn") final String isbn, @FormParam("readerId") final String readerId) {
        final Book book = bookService.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No book with this isbn").build();
        }
        final Reader reader = readerService.getReader(readerId);
        if (reader == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No reader with this id").build();
        }
        lendingService.lendBook(book, reader);
        return Response.ok(book).build();
    }

    @POST
    @Path("/return")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(@FormParam("isbn") final String isbn, @FormParam("readerId") final String readerId) {
        final Book book = bookService.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No book with this isbn").build();
        }
        final Reader reader = readerService.getReader(readerId);
        if (reader == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No reader with this id").build();
        }
        lendingService.returnBook(book, reader);
        return Response.ok(book).build();
    }

    @GET
    @Path("status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loanStatus() {
        final List<BookAvailability> status = lendingService.status();
        return Response.ok(status).build();
    }
}
