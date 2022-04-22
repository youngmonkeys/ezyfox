package com.tvd12.ezyfox.testing.tool.service.impl;

import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.testing.tool.entity.UserEntity;
import com.tvd12.ezyfox.testing.tool.repo.UserRepo;
import com.tvd12.ezyfox.testing.tool.service.UserService;

public class UserServiceImpl extends EzyLoggable implements UserService {

    protected UserRepo userRepo;

    public void save(UserEntity entity) {
        System.out.println("save entity: " + entity);
    }

}