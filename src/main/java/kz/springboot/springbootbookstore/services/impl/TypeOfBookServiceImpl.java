package kz.springboot.springbootbookstore.services.impl;

import kz.springboot.springbootbookstore.entities.TypeOfBook;
import kz.springboot.springbootbookstore.repositories.TypeOfBookRepository;
import kz.springboot.springbootbookstore.services.TypeOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfBookServiceImpl implements TypeOfBookService {

    @Autowired
    TypeOfBookRepository typeOfBookRepository;

    @Override
    public TypeOfBook addTypeOfBook(TypeOfBook typeOfBook) {
        return typeOfBookRepository.save(typeOfBook);
    }

    @Override
    public TypeOfBook saveTypeOfBook(TypeOfBook typeOfBook) {
        return typeOfBookRepository.save(typeOfBook);
    }

    @Override
    public void deleteTypeOfBook(TypeOfBook typeOfBook) {
        typeOfBookRepository.delete(typeOfBook);
    }

    @Override
    public TypeOfBook getTypeOfBook(Long id) {
        return typeOfBookRepository.getById(id);
    }

    @Override
    public List<TypeOfBook> getAllTypeOfBook() {
        return typeOfBookRepository.findAll();
    }
}
