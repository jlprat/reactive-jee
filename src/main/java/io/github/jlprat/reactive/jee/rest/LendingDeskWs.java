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
import java.util.Optional;

/**
 * @author @jpra
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
        final Optional<Book> maybeBook = bookService.getBook(isbn);
        final Optional<Reader> maybeReader = readerService.getReader(readerId);
        return maybeBook
                .map(book -> maybeReader
                        .map(r -> {
                            lendingService.lendBook(book, r);
                            return Response.ok(book);
                        })
                        .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).entity("No reader with this id")))
                .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).entity("No book with this isbn"))
                .build();
    }

    @POST
    @Path("/return")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(@FormParam("isbn") final String isbn, @FormParam("readerId") final String readerId) {
        final Optional<Book> maybeBook = bookService.getBook(isbn);
        final Optional<Reader> maybeReader = readerService.getReader(readerId);
        return maybeBook
                .map(book -> maybeReader
                        .map(r -> {
                            lendingService.returnBook(book, r);
                            return Response.ok(book);
                        })
                        .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).entity("No reader with this id")))
                .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).entity("No book with this isbn"))
                .build();
    }

    @GET
    @Path("status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loanStatus() {
        final List<BookAvailability> status = lendingService.status();
        return Response.ok(status).build();
    }
}
