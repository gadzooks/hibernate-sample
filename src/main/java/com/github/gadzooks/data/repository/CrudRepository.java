package com.github.gadzooks.data.repository;

public interface CrudRepository<TD, ID> {
    TD save(TD entity);
    TD findById(ID id);
    TD update(TD entity, ID id);
    void delete(ID id);
//  TODO  int size();
}
