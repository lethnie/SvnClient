package com.svnclient.service;

import com.svnclient.dao.UserDAO;
import com.svnclient.domain.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Transactional
    public void addUser(UserTable user) {
        userDAO.addUser(user);
    }

    @Transactional
    public List<UserTable> getUsers() {
        return userDAO.getUsers();
    }

    @Transactional
    public UserTable findUser(String name, String password) {
        return userDAO.findUser(name, password);
    }

    @Transactional
    public UserTable findUserById(Integer id) {
        return userDAO.findUserById(id);
    }

    @Transactional
    public UserTable findUserByName(String name) {
        return userDAO.findUserByName(name);
    }

    @Transactional
    public void removeUser(Integer id) {
        userDAO.removeUser(id);
    }
}
