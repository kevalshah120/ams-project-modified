package com.example.splashscreen;

public class teacher_mark_attend_model {
    private String enr_no;
    private String stu_name;
    private boolean atdStatus;

    teacher_mark_attend_model(String enr_no,String stu_name,boolean atdStatus)
    {
        this.enr_no = enr_no;
        this.stu_name = stu_name;
        this.atdStatus = atdStatus;
    }
    public String getStu_name() {
        return stu_name;
    }
    public String getEnr_no() {
        return enr_no;
    }
    public boolean getAtdStatus() {return atdStatus;}
}
