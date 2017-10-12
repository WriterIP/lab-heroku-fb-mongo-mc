package ua.kpi.ip.persistance.model;

public class LoginData {

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
