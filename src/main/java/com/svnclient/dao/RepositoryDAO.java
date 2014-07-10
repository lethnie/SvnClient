package com.svnclient.dao;

import com.svnclient.domain.RepositoryTable;
import com.svnclient.domain.UserTable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 10.07.14
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface RepositoryDAO {
    public void addRep(RepositoryTable repositoryTable);

    public List<RepositoryTable> findRepositoriesByUser(UserTable user);

    public RepositoryTable findRepositoryByNameAndUser(String repository, UserTable user);
}
