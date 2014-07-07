package com.svnclient.service;

import com.svnclient.dao.UserDAO;
import com.svnclient.domain.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly=true)
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        UserTable domainUser = userDAO.findUserByName(login);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(
                domainUser.getName(),
                domainUser.getPassword(),
                authorities
        );
    }
}