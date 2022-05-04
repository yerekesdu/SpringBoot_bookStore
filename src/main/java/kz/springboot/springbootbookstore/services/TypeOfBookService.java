package kz.springboot.springbootbookstore.services;

import kz.springboot.springbootbookstore.entities.TypeOfBook;

import java.lang.reflect.Type;
import java.util.List;

public interface TypeOfBookService {

    TypeOfBook addTypeOfBook(TypeOfBook typeOfBook);

    TypeOfBook saveTypeOfBook(TypeOfBook typeOfBook);

    void deleteTypeOfBook(TypeOfBook typeOfBook);

    TypeOfBook getTypeOfBook(Long id);

    List<TypeOfBook> getAllTypeOfBook();

}
