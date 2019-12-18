package org.launchcode.library.models.data;

import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookDao extends CrudRepository<Book, Integer> {

//    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor(Author author);
}
