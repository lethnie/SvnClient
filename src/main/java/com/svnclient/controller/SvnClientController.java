package com.svnclient.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.auth.SVNAuthentication;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SvnClientController {

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required=false) String auth_status) {
        model.addAttribute("auth_status", auth_status);
        return "index";
    }

    @RequestMapping(value = "/content.html")
    public String content(Model model) {
        String url = "http://svn.svnkit.com/repos/svnkit/trunk/";
        String filePath = "README.txt";
        String file = getContent(url, filePath);
        Integer count = StringUtils.countMatches(file, "\n");
        count++;
        file = file.replace("\n", "<br>");
        model.addAttribute("file", file);
        model.addAttribute("count", count);
        return "content";
    }

    public String getContent(String url, String filePath) {
        String name;
        String password;
        /* TODO: User user =
                (User) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        name = user.getUsername();
        password = user.getPassword();*/
        name = "anonymous";
        password = "anonymous";
        setupLibrary();

        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException svnex) {
            return "wrong url: ".concat(url);
        }

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);

        /*
         * This Map will be used to get the file properties. Each Map key is a
         * property name and the value associated with the key is the property
         * value.
         */
        SVNProperties fileProperties = new SVNProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            SVNNodeKind nodeKind = repository.checkPath(filePath, -1);

            if (nodeKind == SVNNodeKind.NONE) {
                return "wrong file path: ".concat(filePath);
            }
            if (nodeKind == SVNNodeKind.DIR) {
                return "dir path: ".concat(filePath);
            }
            repository.getFile(filePath, -1, fileProperties, baos);

        } catch (SVNException svne) {
            return "error while fetching the file contents and properties: " + svne.getMessage();
        }

        String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
        boolean isTextType = SVNProperty.isTextMimeType(mimeType);

        /*Iterator iterator = fileProperties.nameSet().iterator();

        //Displays file properties.

        while (iterator.hasNext()) {
            String propertyName = (String) iterator.next();
            String propertyValue = fileProperties.getStringValue(propertyName);
            System.out.println("File property: " + propertyName + "="
                    + propertyValue);
        }*/
        /*
         * Displays the file contents in the console if the file is a text.
         */
        String result = "";
        if (isTextType) {
            //System.out.println("File contents:");
            //System.out.println();
            try {
                //baos.writeTo(System.out);
                result = baos.toString("UTF-8");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            return "file contents can not be displayed.";
        }
        //System.out.println(result);
        /*
         * Gets the latest revision number of the repository
         */
        /*long latestRevision = -1;
        try {
            latestRevision = repository.getLatestRevision();
        } catch (SVNException svne) {
            System.err.println("error while fetching the latest repository revision: " + svne.getMessage());
            System.exit(1);
        }
        System.out.println("");
        System.out.println("---------------------------------------------");
        System.out.println("Repository latest revision: " + latestRevision);*/
        return result;
    }

    /*
     * Initializes the library to work with a repository via
     * different protocols.
     */
    private void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
}