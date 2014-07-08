package com.svnclient.dto;

/**
 * Created with IntelliJ IDEA.
 * User: 123
 * Date: 08.07.14
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class FileInfo {
    public FileInfo() {

    }

    private String type;
    private String name;
    private String message;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
