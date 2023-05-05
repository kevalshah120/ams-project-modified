package com.example.splashscreen;

public class today_attendance_taken_model {
    private String subject_name;
    private String student_present_count;
    private String division;

    public String getSubject_name() {
        return subject_name;
    }

    public String getStudent_present_count() {
        return student_present_count;
    }

    public String getDivision() {
        return division;
    }

    today_attendance_taken_model(String subject_name, String student_present_count, String division)
    {
        this.subject_name = subject_name;
        this.student_present_count = student_present_count;
        this.division = division;
    }
}
