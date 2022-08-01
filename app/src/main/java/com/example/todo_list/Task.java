package com.example.todo_list;


import java.util.List;

public class Task {
    private String id;
    private String name;
    private boolean check;
    private String StartTime;
    private String EndTime;
    private String Remind;
   // private List<SubTask> subTasks;
 public Task(){
 }
    public Task(String id, String name, boolean check, String startTime, String endTime, String remind) {
        this.id = id;
        this.name = name;
        this.check = check;
        StartTime = startTime;
        EndTime = endTime;
        Remind = remind;

    }

//    public List<SubTask> getSubTasks() {
//        return subTasks;
//    }
//
//    public void setSubTasks(List<SubTask> subTasks) {
//        this.subTasks = subTasks;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getRemind() {
        return Remind;
    }

    public void setRemind(String remind) {
        Remind = remind;
    }
}
