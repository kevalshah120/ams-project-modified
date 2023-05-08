package com.example.splashscreen;

public class contact_faculty_model {
    private String prof_name;
    private String prof_num;

    public contact_faculty_model(String prof_name, String prof_num) {
        this.prof_name = prof_name;
        this.prof_num = prof_num;
    }
    public String getProf_name() {
        return prof_name;
    }

    public String getProf_num() {
        return prof_num;
    }
}
