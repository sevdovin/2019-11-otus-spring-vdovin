package ru.otus.svdovin.homework09.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework09.domain.Author;
import ru.otus.svdovin.homework09.domain.Book;
import ru.otus.svdovin.homework09.domain.Genre;
import ru.otus.svdovin.homework09.service.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@ShellComponent
public class BookShellCommander {

    private static final String MSG_KEY_AUTHOR_ID_NOT_EXISTS = "authorid.is.not.exists";
    private static final String MSG_KEY_GENRE_ID_NOT_EXISTS = "genreid.is.not.exists";
    private static final String MSG_KEY_BOOK_NAME_IS_EXISTS = "bookname.is.exists";
    private static final String MSG_KEY_BOOK_CREATED = "book.created";
    private static final String MSG_KEY_BOOK_ID_NOT_EXISTS = "bookid.is.not.exists";
    private static final String MSG_KEY_BOOK_PRINT = "book.print";
    private static final String MSG_KEY_BOOK_DELETED = "book.deleted";
    private static final String MSG_KEY_BOOKS_PRINT = "books.print";
    private static final String MSG_KEY_COMMENT_FOR_BOOK_ID_NOT_EXISTS = "comment.is.not.exists.for.book";
    private static final String MSG_KEY_COMMENTS_PRINT = "comments.print";

    private final BookProvider bookProvider;
    private final AuthorProvider authorProvider;
    private final GenreProvider genreProvider;
    private final CommentProvider commentProvider;
    private final MessageService messageService;

    public BookShellCommander(BookProvider bookProvider, AuthorProvider authorProvider, GenreProvider genreProvider,
                              CommentProvider commentProvider, MessageService messageService) {
        this.bookProvider = bookProvider;
        this.authorProvider = authorProvider;
        this.genreProvider = genreProvider;
        this.commentProvider = commentProvider;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Create book", key = {"bc"})
    public void createBook(@ShellOption String bookname, @ShellOption long authorid, @ShellOption long genreid) {
        if (!authorProvider.getAuthorById(authorid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else if (!genreProvider.getGenreById(genreid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else if (bookProvider.existsByName(bookname)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_NAME_IS_EXISTS);
        } else {
            Author author = authorProvider.getAuthorById(authorid).orElse(null);
            List<Author> listAuthor = Collections.singletonList(author);
            Genre genre = genreProvider.getGenreById(genreid).orElse(null);
            long newId = bookProvider.createBook(new Book(0, bookname, genre, listAuthor));
            messageService.printMessageByKey(MSG_KEY_BOOK_CREATED, newId);
        }
    }

    @ShellMethod(value = "Get book by id", key = {"bgi"})
    public void getBookById(@ShellOption long id) {
        if (!bookProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            Book book = bookProvider.getBookById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, book);
        }
    }

    @ShellMethod(value = "Get book by name", key = {"bgn"})
    public void getBookByName(@ShellOption String name) {
        if (!bookProvider.existsByName(name)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            List<Book> bookList = bookProvider.getBookByName(name);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, bookList);
        }
    }

    @ShellMethod(value = "Update book", key = {"bu"})
    public void updateBook(@ShellOption long id, @ShellOption String newname) {
        if (!bookProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            Book book = bookProvider.getBookById(id).orElse(null);
            book.setBookName(newname);
            bookProvider.updateBook(book);
            Book actual = bookProvider.getBookById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, actual);
        }
    }

    @ShellMethod(value = "Delete book", key = {"bd"})
    public void deleteBook(@ShellOption long id) {
        if (!bookProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            bookProvider.deleteBookById(id);
            messageService.printMessageByKey(MSG_KEY_BOOK_DELETED);
        }
    }

    @ShellMethod(value = "Get all books", key = {"bga"})
    public void getBookAll() {
        List<Book> books = bookProvider.getBookAll();
        messageService.printMessageByKey(MSG_KEY_BOOKS_PRINT, books);
    }

    @ShellMethod(value = "Get books by author id", key = {"bgai"})
    public void getBooksByAuthorId(@ShellOption long authorid) {
        if (!authorProvider.getAuthorById(authorid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else {
            List<Book> bookList = bookProvider.getBookByAuthorId(authorid);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, bookList);
        }
    }

    @ShellMethod(value = "Get books by genre id", key = {"bggi"})
    public void getBooksByGenreId(@ShellOption long genreid) {
        if (!genreProvider.getGenreById(genreid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else {
            List<Book> bookList = bookProvider.getBookByGenreId(genreid);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, bookList);
        }
    }

    @ShellMethod(value = "h2 console", key = {"h2"})
    public void h2Console() {
        try {
            Console.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
