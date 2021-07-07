package com.github.gadzooks.data.service;

public interface CrudService<TD, ID> {
    TD save(TD entity);
}
