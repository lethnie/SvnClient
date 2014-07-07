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
@Table(name="user_table")
public class UserTable implements Serializable {
    public static final String id_column = "user_id";
    public static final String name_column = "name";
    public static final String password_column = "password";

    public UserTable() {
    }

    private Integer id;
    private String name;
    private String password;


    @Id
    @Column(name=id_column)
    @GeneratedValue(generator="user_sequence")
    @GenericGenerator(name="user_sequence", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name=name_column, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name=password_column, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
