package kz.springboot.springbootbookstore.repositories;

import kz.springboot.springbootbookstore.entities.TypeOfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TypeOfBookRepository extends JpaRepository<TypeOfBook, Long> {
}
