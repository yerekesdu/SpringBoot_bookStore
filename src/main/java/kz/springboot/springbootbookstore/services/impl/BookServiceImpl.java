package kz.springboot.springbootbookstore.services.impl;

import kz.springboot.springbootbookstore.entities.Books;
import kz.springboot.springbootbookstore.repositories.BookRepository;
import kz.springboot.springbootbookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Books addBook(Books book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Books> getAllBooks() {
        return bookRepository.findAllByAmountGreaterThan(0);
    }

    @Override
    public Books getBook(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public void deleteBook(Books book) {
        bookRepository.delete(book);
    }

    @Override
    public Books saveBook(Books book) {
        return bookRepository.save(book);
    }

}
