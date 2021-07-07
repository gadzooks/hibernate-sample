package com.github.gadzooks.data.repository;

public interface CrudRepository<TD, ID> {
    TD save(TD entity);
    TD findById(ID id);
}
