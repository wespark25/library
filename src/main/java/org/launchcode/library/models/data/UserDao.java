package org.launchcode.library.models.data;

import org.launchcode.library.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
}
