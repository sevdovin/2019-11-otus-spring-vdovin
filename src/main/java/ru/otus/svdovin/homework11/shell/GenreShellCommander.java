package ru.otus.svdovin.homework11.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework11.domain.Genre;
import ru.otus.svdovin.homework11.service.GenreProvider;
import ru.otus.svdovin.homework11.service.MessageService;

import java.util.List;

@ShellComponent
public class GenreShellCommander {

    private static final String MSG_KEY_GENRE_NAME_IS_EXISTS = "genrename.is.exists";
    private static final String MSG_KEY_GENRE_CREATED = "genre.created";
    private static final String MSG_KEY_GENRE_ID_NOT_EXISTS = "genreid.is.not.exists";
    private static final String MSG_KEY_GENRE_PRINT = "genre.print";
    private static final String MSG_KEY_GENRE_DELETED = "genre.deleted";

    private final GenreProvider genreProvider;
    private final MessageService messageService;

    public GenreShellCommander(GenreProvider genreProvider, MessageService messageService) {
        this.genreProvider = genreProvider;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Create genre", key = {"gc"})
    public void createGenre(@ShellOption String newname) {
        if (genreProvider.existsByName(newname)) {
            messageService.printMessageByKey(MSG_KEY_GENRE_NAME_IS_EXISTS);
        } else {
            long newId = genreProvider.createGenre(new Genre(0, newname));
            messageService.printMessageByKey(MSG_KEY_GENRE_CREATED, newId);
        }
    }

    @ShellMethod(value = "Get genre by id", key = {"ggi"})
    public void getGenreById(@ShellOption long id) {
        if (!genreProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else {
            Genre genre = genreProvider.getGenreById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_GENRE_PRINT, genre);
        }
    }

    @ShellMethod(value = "Get genre by name", key = {"ggn"})
    public void getGenreByName(@ShellOption String name) {
        if (!genreProvider.existsByName(name)) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else {
            List<Genre> genreList = genreProvider.getGenreByName(name);
            messageService.printMessageByKey(MSG_KEY_GENRE_PRINT, genreList);
        }
    }

    @ShellMethod(value = "Update genre", key = {"gu"})
    public void updateGenre(@ShellOption long id, @ShellOption String newname) {
        if (!genreProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else {
            Genre genre = new Genre(id, newname);
            genreProvider.updateGenre(genre);
            Genre actual = genreProvider.getGenreById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_GENRE_PRINT, actual);
        }
    }

    @ShellMethod(value = "Delete genre", key = {"gd"})
    public void deleteGenre(@ShellOption long id) {
        if (!genreProvider.existsById(id)) {
            messageService.printMessageByKey(MSG_KEY_GENRE_ID_NOT_EXISTS);
        } else {
            genreProvider.deleteGenreById(id);
            messageService.printMessageByKey(MSG_KEY_GENRE_DELETED);
        }
    }
}
