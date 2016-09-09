package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Book;
import io.github.jlprat.reactive.jee.service.AuthorService;
import io.github.jlprat.reactive.jee.service.BookService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @Inject
    private AuthorService authorService;

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


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response writeBook(@FormParam("author") final String authorId, @FormParam("title") final String title,
                              @FormParam("pages") final int pages) {
        final Author author = authorService.getAuthor(authorId);
        if (author != null) {
            final Book book = bookService.writeBook(author, title, pages);
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("No author with this id").build();
        }
    }

}
