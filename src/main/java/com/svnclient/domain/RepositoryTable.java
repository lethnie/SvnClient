package com.svnclient.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="repository_table")
public class RepositoryTable implements Serializable {
    public static final String id_column = "repository_id";
    public static final String repository_column = "repository";

    public RepositoryTable() {
    }

    private Integer id;
    private UserTable user;
    private String repository;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name=UserTable.id_column)
    public UserTable getUser() {
        return user;
    }

    public void setUser(UserTable user) {
        this.user = user;
    }

    @Column(name=repository_column, nullable = false)
    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Id
    @Column(name=id_column)
    @GeneratedValue(generator="repository_sequence")
    @GenericGenerator(name="repository_sequence", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
