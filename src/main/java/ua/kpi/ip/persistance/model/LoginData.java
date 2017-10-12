package ua.kpi.ip.persistance.model;

import java.io.Serializable;

public class LoginData implements Serializable{

    private String date;
    private String name;

    public LoginData(String date, String name) {
        this.date = date;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
