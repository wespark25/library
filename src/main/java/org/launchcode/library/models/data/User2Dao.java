package org.launchcode.library.models.data;

import org.launchcode.library.models.User2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface User2Dao extends CrudRepository<User2, Integer> {
    public User2 findByUsername(String username);
}
