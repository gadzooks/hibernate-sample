package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.User;
import com.github.gadzooks.data.repository.UserCrudRepository;

public class UserCrudRepositoryImpl extends AbstractJpaCrudRepository<User, Long> implements UserCrudRepository {

    @Override
    protected void updateEntity(User update, User updateFrom) {

    }
}
