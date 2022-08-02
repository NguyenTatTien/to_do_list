package com.example.todo_list;

import java.util.List;

public class UserDetail {
    User user;
    List<Nofication> noficationList;


    public UserDetail(){}
    public UserDetail(User user, List<Nofication> noficationList) {
        this.user = user;
        this.noficationList = noficationList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Nofication> getNoficationList() {
        return noficationList;
    }

    public void setNoficationList(List<Nofication> noficationList) {
        this.noficationList = noficationList;
    }
}
