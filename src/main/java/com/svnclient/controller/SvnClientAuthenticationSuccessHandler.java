package com.svnclient.controller;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.util.Set;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
public class SvnClientAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            response.sendRedirect("index.html");
            return;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
