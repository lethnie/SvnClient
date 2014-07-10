package com.svnclient.controller;

import com.svnclient.domain.RepositoryTable;
import com.svnclient.domain.UserTable;
import com.svnclient.dto.FileInfo;
import com.svnclient.service.RepositoryService;
import com.svnclient.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 07.07.14
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SvnClientController {
    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required=false) String auth_status) {
        model.addAttribute("auth_status", auth_status);
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal().toString());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            model.addAttribute("auth_status", auth_status);
            return "index";
        }
        else {
            if ( auth.getPrincipal().equals("guest")) {
                model.addAttribute("auth_status", auth_status);
                return "index";
            } else {
                return "redirect:directory.html";
            }
        }
    }

    //get first directory content
    @RequestMapping(value = "/directory.html")
    public String content(Model model) {
        //TODO: correct url
        String url = "http://svn.svnkit.com/repos/svnkit/trunk/";
        String filePath = "";

        String name;
        String password;
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal().toString());
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        name = user.getUsername();
        password = user.getPassword();
        //TODO: delete
        System.out.println(name + " " + password);
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

        try {
            SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
            if (nodeKind == SVNNodeKind.DIR) {
                List<FileInfo> files = getDirContent(repository, filePath);
                model.addAttribute("files", files);
                return "directory";
            }
            if (nodeKind == SVNNodeKind.FILE) {
                String file = getFileContent(repository, filePath);
                Integer count = StringUtils.countMatches(file, "\n");
                count++;
                file = file.replace("\n", "<br>");
                model.addAttribute("file", file);
                model.addAttribute("count", count);
                return "file";
            }
            model.addAttribute("file", "wrong file path: ".concat(filePath));
            model.addAttribute("count", 1);
            return "file";
        } catch (SVNException svne) {
            model.addAttribute("file", "error while fetching the file contents and properties: " + svne.getMessage());
            model.addAttribute("count", 1);
            return "file";
        }
    }

    @RequestMapping(value = "/new.html")
    public String getNewRep(Map<String, Object> map, Model model) {
        map.put("rep", new RepositoryTable());
        return "new";
    }

    @RequestMapping(value = "/create.html", method = RequestMethod.POST)
    public String createRep(@ModelAttribute("rep") RepositoryTable rep, Model model) {
        String username = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
        UserTable user = userService.findUserByName(username);
        String url = String.format("/svn/%s/%s", username, rep.getRepository());
        //TODO: smth
        String localURL = String.format("/svn/%s/%s", username, rep.getRepository());
        rep.setUser(user);
        rep.setLocalURL(localURL);
        rep.setUrl(url);


        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(user.getName(), user.getPassword());
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNAdminClient svnAdminClient = new SVNAdminClient(authManager, options);
        try {
            SVNURL svnurl = svnAdminClient.doCreateRepository(new File(localURL), null, true, false);
            rep.setLocalURL(svnurl.toString());
        }
        catch (SVNException ex) {
            System.out.println(ex.getMessage());
        }
        repositoryService.addRep(rep);
        return "redirect:".concat(url).concat(".html");
    }

    //TODO: server???
    @RequestMapping(value = "/svn/{username}/{name}")
    public String getSvnRep(Model model, @PathVariable("username") String username, @PathVariable("name") String name) {
        //System.out.println("SVN!!! " + username + " " + name);

        String usern = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
        UserTable user = userService.findUserByName(usern);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(user.getName(), user.getPassword());
        RepositoryTable repositoryTable = repositoryService.findRepositoryByName(name);
        String url = repositoryTable.getLocalURL();
        String filePath = "";

        setupLibrary();

        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException svnex) {
            return "wrong url: ".concat(url);
        }

        repository.setAuthenticationManager(authManager);

        try {
            SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
            if (nodeKind == SVNNodeKind.DIR) {
                List<FileInfo> files = getDirContent(repository, filePath);
                model.addAttribute("files", files);
                return "directory";
            }
            if (nodeKind == SVNNodeKind.FILE) {
                String file = getFileContent(repository, filePath);
                Integer count = StringUtils.countMatches(file, "\n");
                count++;
                file = file.replace("\n", "<br>");
                model.addAttribute("file", file);
                model.addAttribute("count", count);
                return "file";
            }
            model.addAttribute("file", "wrong file path: ".concat(filePath));
            model.addAttribute("count", 1);
            return "file";
        } catch (SVNException svne) {
            model.addAttribute("file", "error while fetching the file contents and properties: " + svne.getMessage());
            model.addAttribute("count", 1);
            return "file";
        }
    }

    @RequestMapping(value = "/repositories.html")
    public String getRepositories(Model model) {
        String usern = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();
        UserTable user = userService.findUserByName(usern);
        List<RepositoryTable> repositories = repositoryService.findRepositoriesByUser(user);
        List<FileInfo> files = new ArrayList<FileInfo>();
        for (RepositoryTable repositoryTable : repositories) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setAuthor(user.getName());
            fileInfo.setName(repositoryTable.getRepository());
            fileInfo.setType("dir");
            files.add(fileInfo);
        }
        model.addAttribute("files", files);
        return "directory";
    }

    public String getFileContent(SVNRepository repository, String filePath) {
        String result = "";
        SVNProperties fileProperties = new SVNProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            repository.getFile(filePath, -1, fileProperties, baos);
        } catch (SVNException ex) {
            return "error while fetching the file contents and properties: " + ex.getMessage();
        }
        String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
        boolean isTextType = SVNProperty.isTextMimeType(mimeType);
        if (isTextType) {
            try {
                result = baos.toString("UTF-8");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            return "file contents can not be displayed.";
        }
        return result;
    }

    public List<FileInfo> getDirContent(SVNRepository repository, String filePath) {
        ArrayList<FileInfo> result = new ArrayList<FileInfo>();
        SVNProperties dirProperties = new SVNProperties();
        try {
            Collection dirs = repository.getDir(filePath, -1, dirProperties, (Collection) null);
            Iterator iterator = dirs.iterator();
            while (iterator.hasNext()) {
                SVNDirEntry entry = (SVNDirEntry)iterator.next();
                FileInfo fileInfo = new FileInfo();
                if (entry.getKind() == SVNNodeKind.DIR) {
                    fileInfo.setType("dir");
                } else {
                    if (entry.getKind() == SVNNodeKind.FILE) {
                        fileInfo.setType("file");
                    } else {
                        fileInfo.setType("unknown");
                    }
                }
                fileInfo.setName(entry.getName());
                fileInfo.setAuthor(entry.getAuthor());
                fileInfo.setMessage(entry.getCommitMessage());
                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                String date = f.format(entry.getDate());
                fileInfo.setDate(date);
                result.add(fileInfo);
            }
        } catch (SVNException ex) {
            return null;
        }
        return result;
    }

    //registration
    @RequestMapping(value="/add_user.html", method=RequestMethod.POST)
    public @ResponseBody
    String addUser(@RequestBody String param) {

        String name = "";
        String pass = "";

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(param);

            JSONObject jsonObject = (JSONObject) obj;

            name = (String) jsonObject.get("name");
            pass = (String) jsonObject.get("pass");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        if (userService.findUserByName(name) != null) {
            return "error";
        }

        UserTable userTable = new UserTable();
        userTable.setName(name);
        userTable.setPassword(pass);
        userService.addUser(userTable);

        return "ok";
    }

    //get file or directory content by filepath
    @RequestMapping(value="/get_data.html", method=RequestMethod.POST)
    public @ResponseBody
    String getData(@RequestBody String param) {

        String filepath = "";

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(param);

            JSONObject jsonObject = (JSONObject) obj;

            filepath = (String) jsonObject.get("filepath");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        filepath = filepath.replaceFirst("..", "");
        filepath = filepath.replaceFirst("/", "");
        filepath = filepath.replace(" ","");
        //TODO: correct url
        String url = "http://svn.svnkit.com/repos/svnkit/trunk/";

        String name;
        String password;
        //TODO: delete
        System.out.println(SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal().toString());
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        name = user.getUsername();
        password = userService.findUserByName(user.getUsername()).getPassword();
        //TODO: delete
        //TODO: passwords???
        System.out.println(name + " " + password);
        //name = "anonymous";
        //password = "anonymous";

        setupLibrary();

        SVNRepository repository = null;
        System.out.println("PATH: " + filepath + ", URL: " + url);
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        } catch (SVNException svnex) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "error");
            return jsonObject.toJSONString();
        }

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);

        try {
            SVNNodeKind nodeKind = repository.checkPath(filepath, -1);
            if (nodeKind == SVNNodeKind.DIR) {
                List<FileInfo> files = getDirContent(repository, filepath);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "dir");

                JSONArray jsonArray = new JSONArray();

                for (FileInfo fileInfo : files) {
                    JSONObject obj = new JSONObject();
                    obj.put("type", fileInfo.getType());
                    obj.put("name", fileInfo.getName());
                    obj.put("message", fileInfo.getMessage());
                    obj.put("author", fileInfo.getAuthor());
                    obj.put("date", fileInfo.getDate());
                    jsonArray.add(obj);
                }
                jsonObject.put("files", jsonArray);
                return jsonObject.toJSONString();
            }
            if (nodeKind == SVNNodeKind.FILE) {
                String file = getFileContent(repository, filepath);
                file = file.replace("\n", "<br>");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "file");
                jsonObject.put("file", file);
                return jsonObject.toJSONString();
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "unknown");
            return jsonObject.toJSONString();
        } catch (SVNException svne) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "error");
            return jsonObject.toJSONString();
        }
    }

    //download file by filepath
    @RequestMapping(value = "/get_file.html", method=RequestMethod.GET)
    public void downloadFile(@RequestParam String filepath,
                             final HttpServletResponse response) throws IOException {
        try {
            //TODO: correct url
            SVNURL url=SVNURL.parseURIEncoded("http://svn.svnkit.com/repos/svnkit/trunk/");
            SVNRepository repository = SVNRepositoryFactory.create(url);
            OutputStream outputStream = response.getOutputStream();
            filepath = java.net.URLDecoder.decode(filepath, "UTF-8");
            filepath = filepath.replaceFirst("/", "");
            //System.out.println("GET FILE: " + filepath);
            repository.getFile(filepath, -1, null, outputStream);
            //System.out.println("GET FILE");
            response.setContentType("application/force-download");
            int index = filepath.lastIndexOf('/');
            String filename = filepath.substring(index + 1);
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));
            outputStream.close();
        } catch (SVNException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
}