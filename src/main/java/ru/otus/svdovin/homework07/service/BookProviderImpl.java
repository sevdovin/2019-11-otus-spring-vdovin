package ru.otus.svdovin.homework07.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework07.dao.BookDao;
import ru.otus.svdovin.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookDao bookDao;
    private final MessageService ms;

    public BookProviderImpl(BookDao bookDao, MessageService ms) {
        this.bookDao = bookDao;
        this.ms = ms;
    }

    @Override
    public long createBook(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getBookByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    public List<Book> getBookAll() {
        return bookDao.getAll();
    }

    @Override
    public List<Book> getBookByAuthorId(long authorId) {
        return bookDao.getByAuthorId(authorId);
    }

    @Override
    public List<Book> getBookByGenreId(long genreId) {
        return bookDao.getByGenreId(genreId);
    }
}
