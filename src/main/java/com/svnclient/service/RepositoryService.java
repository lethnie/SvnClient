package com.svnclient.service;

import com.svnclient.domain.RepositoryTable;
import com.svnclient.domain.UserTable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 10.07.14
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public interface RepositoryService {
    public void addRep(RepositoryTable repositoryTable);

    public List<RepositoryTable> findRepositoriesByUser(UserTable user);

    public RepositoryTable findRepositoryByName(String repository);
}
