package com.example.splashscreen;

public class leave_model_class {
    private int tag;
    private String leave_name;
    private String date;
    private String prof_name;
    private int teacher_icon;

    leave_model_class(int tag,String leave_name,String date,String prof_name,int teacher_icon)
    {
        this.tag = tag;
        this.leave_name = leave_name;
        this.date = date;
        this.prof_name = prof_name;
        this.teacher_icon = teacher_icon;
    }

    public int getTag() {
        return tag;
    }

    public String getLeave_name() {
        return leave_name;
    }

    public String getDate() {
        return date;
    }

    public String getProf_name() {
        return prof_name;
    }

    public int getTeacher_icon() {
        return teacher_icon;
    }

}
