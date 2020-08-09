package ru.otus.svdovin.homework24.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework24.domain.AclEntry;
import ru.otus.svdovin.homework24.domain.Book;
import ru.otus.svdovin.homework24.repository.AclEntryRepository;
import ru.otus.svdovin.homework24.repository.AclSidRepository;
import ru.otus.svdovin.homework24.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookProviderImpl implements BookProvider {

    private final BookRepository bookRepository;
    private final AclEntryRepository aclEntryRepository;
    private final AclSidRepository aclSidRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public long createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        addAclEntries(book);
        return savedBook.getBookId();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getBookByName(String name) {
        return bookRepository.findByBookName(name);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getBookAll() {
        return bookRepository.findAll();
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getBookByAuthorId(long authorId) {
        return bookRepository.findByAuthorsAuthorId(authorId);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getBookByGenreId(long genreId) {
        return bookRepository.findByGenreGenreId(genreId);
    }

    @Override
    public boolean existsById(long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByBookName(name);
    }

    private void addAclEntries(Book book) {
        Long adminSidId = aclSidRepository.findBySid("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("ROLE_ADMIN is unavailable")).getId();
        Long userSidId = aclSidRepository.findBySid("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER is unavailable")).getId();
        Long userRomanSidId = aclSidRepository.findBySid("ROLE_ROMAN").orElseThrow(() -> new RuntimeException("ROLE_ROMAN is unavailable")).getId();
        Long userComedySidId = aclSidRepository.findBySid("ROLE_COMEDY").orElseThrow(() -> new RuntimeException("ROLE_COMEDY is unavailable")).getId();

        List<AclEntry> aclEntryList = new ArrayList<>();
        aclEntryList.add(
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(1)
                        .mask(BasePermission.READ.getMask())
                        .sid(adminSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build()
        );
        aclEntryList.add(
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(2)
                        .mask(BasePermission.WRITE.getMask())
                        .sid(adminSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build()
        );
        aclEntryList.add(
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(3)
                        .mask(BasePermission.READ.getMask())
                        .sid(userSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build()
        );
        if (book.getGenre().getGenreId() == 1) {
            aclEntryList.add(
                    AclEntry.builder()
                            .aclObjectIdentity(book.getId())
                            .aceOrder(4)
                            .mask(BasePermission.READ.getMask())
                            .sid(userRomanSidId)
                            .granting(true)
                            .auditSuccess(true)
                            .auditFailure(true)
                            .build()
            );
        }
        if (book.getGenre().getGenreId() == 3) {
            aclEntryList.add(
                    AclEntry.builder()
                            .aclObjectIdentity(book.getId())
                            .aceOrder(4)
                            .mask(BasePermission.READ.getMask())
                            .sid(userComedySidId)
                            .granting(true)
                            .auditSuccess(true)
                            .auditFailure(true)
                            .build()
            );
        }
        aclEntryRepository.saveAll(aclEntryList);
    }
}
