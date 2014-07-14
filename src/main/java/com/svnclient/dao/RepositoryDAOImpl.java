package com.svnclient.dao;

import com.svnclient.domain.RepositoryTable;
import com.svnclient.domain.UserTable;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 10.07.14
 * Time: 18:36
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RepositoryDAOImpl implements RepositoryDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void addRep(RepositoryTable repositoryTable) {
        sessionFactory.getCurrentSession().save(repositoryTable);
    }

    public List<RepositoryTable> findRepositoriesByUser(UserTable user) {
        List<RepositoryTable> result = sessionFactory.getCurrentSession().createCriteria(RepositoryTable.class)
                .add(Restrictions.eq("user", user))
                .list();
        for (RepositoryTable repositoryTable : result) {
            Hibernate.initialize(repositoryTable.getUser());
        }
        return result;
    }

    public RepositoryTable findRepositoryByNameAndUser(String repository, UserTable user) {
        RepositoryTable result = (RepositoryTable)sessionFactory.getCurrentSession().createCriteria(RepositoryTable.class)
                .add(Restrictions.eq("repository", repository))
                .add(Restrictions.eq("user", user))
                .uniqueResult();
        if (result != null)
            Hibernate.initialize(result.getUser());
        return result;
    }

    public void removeRepository(Integer id) {
        RepositoryTable repositoryTable = (RepositoryTable) sessionFactory.getCurrentSession().load(
                RepositoryTable.class, id);
        if (null != repositoryTable) {
            sessionFactory.getCurrentSession().delete(repositoryTable);
        }
    }
}
