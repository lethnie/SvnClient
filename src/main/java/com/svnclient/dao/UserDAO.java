package com.svnclient.dao;

import com.svnclient.domain.UserTable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {
    public void addUser(UserTable user);

    public List<UserTable> getUsers();

    public UserTable findUser(String name, String password);

    public UserTable findUserById(Integer id);

    public UserTable findUserByName(String name);

    public void removeUser(Integer id);
}
