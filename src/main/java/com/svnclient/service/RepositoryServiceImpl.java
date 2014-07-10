package com.svnclient.service;

import com.svnclient.dao.RepositoryDAO;
import com.svnclient.domain.RepositoryTable;
import com.svnclient.domain.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 10.07.14
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private RepositoryDAO repositoryDAO;

    @Transactional
    public void addRep(RepositoryTable repositoryTable) {
        repositoryDAO.addRep(repositoryTable);
    }

    @Transactional
    public List<RepositoryTable> findRepositoriesByUser(UserTable user) {
        return repositoryDAO.findRepositoriesByUser(user);
    }

    @Transactional
    public RepositoryTable findRepositoryByNameAndUser(String repository, UserTable user) {
        return repositoryDAO.findRepositoryByNameAndUser(repository, user);
    }
}
