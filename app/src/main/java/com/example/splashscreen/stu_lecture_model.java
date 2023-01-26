package com.example.splashscreen;

public class stu_lecture_model {
    private String subject_name;
    private String prof_name;

    stu_lecture_model(String subject_name,String prof_name)
    {
        this.subject_name = subject_name;
        this.prof_name = prof_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getProf_name() {
        return prof_name;
    }
}
