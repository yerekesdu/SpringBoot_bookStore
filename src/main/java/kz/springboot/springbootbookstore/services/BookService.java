package kz.springboot.springbootbookstore.services;

import kz.springboot.springbootbookstore.entities.Books;

import java.util.List;

public interface BookService {

    Books addBook(Books book);

    List<Books> getAllBooks();

    Books getBook(Long id);

    void deleteBook(Books book);

    Books saveBook(Books book);

}
