package com.example.splashscreen;

public class teacher_student_model {
    private String enr_no;
    private String stu_name;
    private String image;

    public String getImage() {
        return image;
    }

    teacher_student_model(String enr_no, String stu_name, String image)
    {
        this.enr_no = enr_no;
        this.stu_name = stu_name;
        this.image = image;
    }
    public String getStu_name() {
        return stu_name;
    }
    public String getEnr_no() {
        return enr_no;
    }
}
