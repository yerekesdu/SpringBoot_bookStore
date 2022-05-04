package kz.springboot.springbootbookstore.repositories;

import kz.springboot.springbootbookstore.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Books, Long> {

    List<Books> findAllByAmountGreaterThan(int amount);

}
