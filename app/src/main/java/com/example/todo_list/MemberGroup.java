package com.example.todo_list;

public class MemberGroup {
    int id;
    int idGroup;
    int idUser;

    public MemberGroup(int id, int idGroup, int idUser) {
        this.id = id;
        this.idGroup = idGroup;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
