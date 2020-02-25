package ru.otus.svdovin.homework07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework07.domain.Author;
import ru.otus.svdovin.homework07.service.AuthorProvider;
import ru.otus.svdovin.homework07.service.MessageService;

import java.util.List;

@ShellComponent
public class AuthorShellCommander {

    private static final String MSG_KEY_AUTHOR_NAME_IS_EXISTS = "authorname.is.exists";
    private static final String MSG_KEY_AUTHOR_CREATED = "author.created";
    private static final String MSG_KEY_AUTHOR_ID_NOT_EXISTS = "authorid.is.not.exists";
    private static final String MSG_KEY_AUTHOR_PRINT = "author.print";
    private static final String MSG_KEY_AUTHOR_DELETED = "author.deleted";

    private final AuthorProvider authorProvider;
    private final MessageService messageService;

    public AuthorShellCommander(AuthorProvider authorProvider, MessageService messageService) {
        this.authorProvider = authorProvider;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Create author", key = {"ac"})
    public void createAuthor(@ShellOption String newname) {
        if (isAuthorExists(newname)) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_NAME_IS_EXISTS);
        } else {
            long newId = authorProvider.createAuthor(new Author(0, newname));
            messageService.printMessageByKey(MSG_KEY_AUTHOR_CREATED, newId);
        }
    }

    @ShellMethod(value = "Get author by id", key = {"agi"})
    public void getAuthorById(@ShellOption long id) {
        if (!isAuthorExists(id)) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else {
            Author author = authorProvider.getAuthorById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_AUTHOR_PRINT, author);
        }
    }

    @ShellMethod(value = "Get author by name", key = {"agn"})
    public void getAuthorByName(@ShellOption String name) {
        if (!isAuthorExists(name)) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else {
            List<Author> authorList = authorProvider.getAuthorByName(name);
            messageService.printMessageByKey(MSG_KEY_AUTHOR_PRINT, authorList);
        }
    }

    @ShellMethod(value = "Update author", key = {"au"})
    public void updateAuthor(@ShellOption long id, @ShellOption String newname) {
        if (!isAuthorExists(id)) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else {
            Author author = new Author(id, newname);
            authorProvider.updateAuthor(author);
            Author actual = authorProvider.getAuthorById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_AUTHOR_PRINT, actual);
        }
    }

    @ShellMethod(value = "Delete author", key = {"ad"})
    public void deleteAuthor(@ShellOption long id) {
        if (!isAuthorExists(id)) {
            messageService.printMessageByKey(MSG_KEY_AUTHOR_ID_NOT_EXISTS);
        } else {
            authorProvider.deleteAuthorById(id);
            messageService.printMessageByKey(MSG_KEY_AUTHOR_DELETED);
        }
    }

    private boolean isAuthorExists(String name) {
        return !authorProvider.getAuthorByName(name).isEmpty();
    }

    private boolean isAuthorExists(long id) {
        return authorProvider.getAuthorById(id).isPresent();
    }
}