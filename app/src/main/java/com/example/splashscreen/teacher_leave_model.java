package com.example.splashscreen;

public class teacher_leave_model {
    private String leave_name;
    private String date;
    private String sem_no;
    private String stu_name;
    private String leave_id;

    teacher_leave_model(String leave_name,String date,String stu_name,String sem_no,String leave_id)
    {
        this.leave_name = leave_name;
        this.date = date;
        this.stu_name = stu_name;
        this.sem_no = sem_no;
        this.leave_id=leave_id;
    }


    public String getLeave_name() {
        return leave_name;
    }
    public String getDate() {
        return date;
    }
    public String getSem_no() {
        return sem_no;
    }
    public String getStu_name() {
        return stu_name;
    }
    public String getLeave_id() {
        return leave_id;
    }
}
