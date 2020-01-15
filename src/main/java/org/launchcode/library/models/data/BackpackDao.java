package org.launchcode.library.models.data;

import org.launchcode.library.models.Backpack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BackpackDao extends CrudRepository<Backpack, Integer> {

}
