package io.github.jlprat.reactive.jee.rest;

import io.github.jlprat.reactive.jee.domain.Author;
import io.github.jlprat.reactive.jee.domain.Person;
import io.github.jlprat.reactive.jee.domain.Reader;
import io.github.jlprat.reactive.jee.service.AuthorService;
import io.github.jlprat.reactive.jee.service.ReaderService;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @jlprat
 */
@WebServlet(urlPatterns = "/servlet/users", asyncSupported = true)
public class UserServlet extends HttpServlet {

    @Inject
    private AuthorService authorService;

    @Inject
    private ReaderService readerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                final List<Author> authors = authorService.getAuthorsSync();
                final List<Reader> readers = readerService.getReadersSync();
                final List<Person> users = new ArrayList<>(authors.size() + readers.size());
                users.addAll(authors);
                users.addAll(readers);
                final ServletOutputStream out = asyncContext.getResponse().getOutputStream();
                for (Person user : users) {
                    out.println(user.toString());
                }
                asyncContext.complete();
            } catch (IOException e) {
                throw new IllegalStateException("Problem with IO", e);
            }
        });
    }
}
