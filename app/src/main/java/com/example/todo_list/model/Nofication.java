package com.example.todo_list.model;

import java.io.Serializable;

public class Nofication implements Serializable {
    String id;
    String title;
    String content;
    boolean status;

    public Nofication(){}
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Nofication(String id, String title, String content, boolean status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
