package ru.otus.svdovin.homework07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework07.domain.Author;
import ru.otus.svdovin.homework07.domain.Book;
import ru.otus.svdovin.homework07.domain.Genre;
import ru.otus.svdovin.homework07.service.AuthorProvider;
import ru.otus.svdovin.homework07.service.BookProvider;
import ru.otus.svdovin.homework07.service.GenreProvider;
import ru.otus.svdovin.homework07.service.MessageService;

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

    private final BookProvider bookProvider;
    private final AuthorProvider authorProvider;
    private final GenreProvider genreProvider;
    private final MessageService messageService;

    public BookShellCommander(BookProvider bookProvider, AuthorProvider authorProvider, GenreProvider genreProvider, MessageService messageService) {
        this.bookProvider = bookProvider;
        this.authorProvider = authorProvider;
        this.genreProvider = genreProvider;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Create book", key = {"bc"})
    public void createBook(@ShellOption String bookname, @ShellOption long authorid, @ShellOption long genreid) {
        if (!authorProvider.getAuthorById(authorid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else if (!genreProvider.getGenreById(genreid).isPresent()) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else if (isBookExists(bookname)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_NAME_IS_EXISTS);
        } else {
            Author author = authorProvider.getAuthorById(authorid).orElse(null);
            Genre genre = genreProvider.getGenreById(genreid).orElse(null);
            long newId = bookProvider.createBook(new Book(0, bookname, author, genre));
            messageService.printMessageByKey(MSG_KEY_BOOK_CREATED, newId);
        }
    }

    @ShellMethod(value = "Get book by id", key = {"bgi"})
    public void getBookById(@ShellOption long id) {
        if (!isBookExists(id)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            Book book = bookProvider.getBookById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, book);
        }
    }


    @ShellMethod(value = "Get book by name", key = {"bgn"})
    public void getBookByName(@ShellOption String name) {
        if (!isBookExists(name)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            List<Book> bookList = bookProvider.getBookByName(name);
            messageService.printMessageByKey(MSG_KEY_BOOK_PRINT, bookList);
        }
    }

    @ShellMethod(value = "Update book", key = {"bu"})
    public void updateBook(@ShellOption long id, @ShellOption String newname) {
        if (!isBookExists(id)) {
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
        if (!isBookExists(id)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            bookProvider.deleteBookById(id);
            messageService.printMessageByKey(MSG_KEY_BOOK_DELETED);
        }
    }

    private boolean isBookExists(String name) {
        return !bookProvider.getBookByName(name).isEmpty();
    }

    private boolean isBookExists(long id) {
        return bookProvider.getBookById(id).isPresent();
    }
}
