package com.example.splashscreen;

public class stu_lecture_model {
    private String subject_name;
    private String prof_name;
    private int staff_id;
    private String subject_code;
    private String LAB;
    private String location;


    stu_lecture_model(String subject_name, String prof_name, int staff_id, String subject_code, String LAB, String location) {
        this.subject_name = subject_name;
        this.prof_name = prof_name;
        this.staff_id = staff_id;
        this.subject_code = subject_code;
        this.LAB = LAB;
        this.location = location;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public String getLAB() {
        return LAB;
    }

    public String getProf_name() {
        return prof_name;
    }

    public String getLocation() {
        return location;
    }
}
