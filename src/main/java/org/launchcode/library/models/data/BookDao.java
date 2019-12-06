package org.launchcode.library.models.data;

import org.launchcode.library.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BookDao extends CrudRepository<Book, Integer> {
}
