package com.github.gadzooks.data.service;

public interface CrudService<TD, ID> {
    TD save(TD entity);
    TD findById(ID id);
    TD update(TD entity, ID id);
    void delete(ID id);
}
