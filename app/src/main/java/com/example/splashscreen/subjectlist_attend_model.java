package com.example.splashscreen;

public class subjectlist_attend_model {
    private String subject_name;
    private String attend_percentage;
    subjectlist_attend_model(String subject_name,String attend_percentage)
    {
        this.subject_name = subject_name;
        this.attend_percentage = attend_percentage;
    }
    public String getSubject_name() {
        return subject_name;
    }
    public String getAttend_percentage() {
        return attend_percentage;
    }
}
