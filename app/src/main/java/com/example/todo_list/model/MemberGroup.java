package com.example.todo_list.model;

import java.util.List;

public class MemberGroup {
    User member;
    List<Task> task;

    public MemberGroup(){}
    public MemberGroup(User member, List<Task> task) {
        this.member = member;
        this.task = task;
    }

    public List<Task> getTaskList() {
        return task;
    }

    public void setTaskList(List<Task> taskList) {
        this.task = taskList;
    }
}
