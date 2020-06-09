package ru.otus.svdovin.homework13.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework13.domain.Book;
import ru.otus.svdovin.homework13.domain.Comment;
import ru.otus.svdovin.homework13.service.BookProvider;
import ru.otus.svdovin.homework13.service.CommentProvider;
import ru.otus.svdovin.homework13.service.MessageService;

import java.util.List;

@ShellComponent
public class CommentShellCommander {

    private static final String MSG_KEY_BOOK_ID_NOT_EXISTS = "bookid.is.not.exists";
    private static final String MSG_KEY_COMMENT_CREATED = "comment.created";
    private static final String MSG_KEY_COMMENT_ID_NOT_EXISTS = "comment.is.not.exists";
    private static final String MSG_KEY_COMMENT_DELETED = "comment.deleted";
    private static final String MSG_KEY_COMMENT_PRINT = "comment.print";
    private static final String MSG_KEY_COMMENT_FOR_BOOK_ID_NOT_EXISTS = "comment.is.not.exists.for.book";
    private static final String MSG_KEY_COMMENTS_PRINT = "comments.print";

    private final CommentProvider commentProvider;
    private final BookProvider bookProvider;
    private final MessageService messageService;

    public CommentShellCommander(CommentProvider commentProvider, BookProvider bookProvider, MessageService messageService) {
        this.commentProvider = commentProvider;
        this.bookProvider = bookProvider;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Create comment", key = {"cc"})
    public void createComment(@ShellOption String bookid, String text) {
        if (!bookProvider.existsById(bookid)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else {
            Book book = bookProvider.getBookById(bookid).orElse(null);
            String newId = commentProvider.createComment(Comment.builder().comment(text).book(book).build());
            messageService.printMessageByKey(MSG_KEY_COMMENT_CREATED, newId);
        }
    }

    @ShellMethod(value = "Delete comment", key = {"cd"})
    public void deleteComment(@ShellOption String id) {
        if (!commentProvider.existsCommentById(id)) {
            messageService.printMessageByKey(MSG_KEY_COMMENT_ID_NOT_EXISTS);
        } else {
            commentProvider.deleteCommentById(id);
            messageService.printMessageByKey(MSG_KEY_COMMENT_DELETED);
        }
    }

    @ShellMethod(value = "Get comment by id", key = {"cgi"})
    public void getCommentById(@ShellOption String id) {
        if (!commentProvider.existsCommentById(id)) {
            messageService.printMessageByKey(MSG_KEY_COMMENT_ID_NOT_EXISTS);
        } else {
            Comment comment = commentProvider.getCommentById(id).orElse(null);
            messageService.printMessageByKey(MSG_KEY_COMMENT_PRINT, comment);
        }
    }

    @ShellMethod(value = "Get comments by book id", key = {"cgbi"})
    public void getCommentsByBookId(@ShellOption String bookid) {
        if (!bookProvider.existsById(bookid)) {
            messageService.printMessageByKey(MSG_KEY_BOOK_ID_NOT_EXISTS);
        } else if (!commentProvider.existsCommentByBookId(bookid)) {
            messageService.printMessageByKey(MSG_KEY_COMMENT_FOR_BOOK_ID_NOT_EXISTS);
        } else {
            List<Comment> comments = commentProvider.getCommentsByBookId(bookid);
            messageService.printMessageByKey(MSG_KEY_COMMENTS_PRINT, comments);
        }
    }
}
