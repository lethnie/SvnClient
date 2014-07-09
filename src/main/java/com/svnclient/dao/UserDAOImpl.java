package com.svnclient.dao;

import com.svnclient.domain.UserTable;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserTable: 123
 * Date: 07.07.14
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(UserTable user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }

    @SuppressWarnings("unchecked")
    public List<UserTable> getUsers() {

        return sessionFactory.getCurrentSession().createQuery("from com.svnclient.domain.UserTable")
                .list();
    }

    @SuppressWarnings("unchecked")
    public UserTable findUser(String name, String password) {
        UserTable result = (UserTable)sessionFactory.getCurrentSession().createCriteria(UserTable.class)
                .add(Expression.like(UserTable.name_column, name))
                .add(Expression.like(UserTable.password_column, password))
                .uniqueResult();
        return result;
    }

    public UserTable findUserById(Integer id) {
        UserTable result = (UserTable)sessionFactory.getCurrentSession().createCriteria(UserTable.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        return result;
    }

    public UserTable findUserByName(String name) {
        UserTable result = (UserTable)sessionFactory.getCurrentSession().createCriteria(UserTable.class)
                .add(Restrictions.eq(UserTable.name_column, name))
                .uniqueResult();
        return result;
    }

    public void removeUser(Integer id) {
        UserTable user = (UserTable) sessionFactory.getCurrentSession().load(
                UserTable.class, id);
        if (null != user) {
            sessionFactory.getCurrentSession().delete(user);
        }

    }
}
