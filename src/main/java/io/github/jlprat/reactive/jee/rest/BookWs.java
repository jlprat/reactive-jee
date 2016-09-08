package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.service.BookService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Rest end point for users.
 * It returns all users in system, authors and readers
 * @author @jlprat
 */
@Stateless
@Path("/books")
public class BookWs {

    @Inject
    private BookService bookService;

    @GET
    public Response getBooks() {
        return Response.ok(bookService.getBooks()).build();
    }

    @Path("/{isbn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") final String isbn) {
        final Book book = bookService.getBook(isbn);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
