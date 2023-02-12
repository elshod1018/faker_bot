package com.company.services;

import com.company.daos.UserDao;
import com.company.domains.UserDomain;
import com.company.dto.Response;
import com.company.utils.BaseUtils;
import com.company.utils.PasswordEncoderUtils;
import lombok.NonNull;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Response<Void> create(@NonNull UserDomain domain) {
        try {
            domain.setPassword(PasswordEncoderUtils.encode(domain.getPassword()));
            userDao.save(domain);
            return new Response<>(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response<>("Something is wrong try later again", BaseUtils.getStackStraceAsString(e));
        }
    }

    public Response<List<UserDomain>> getAllUsers() {
        try {
            return new Response<>(userDao.findAll());
        } catch (SQLException e) {
            // TODO: 05/02/23 log
            e.printStackTrace();
            return new Response<>(e.getMessage(), BaseUtils.getStackStraceAsString(e));
        }
    }
}
