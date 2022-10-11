package com.example.justquiz;

public class Model {
    String id, firstname, lastname, score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Model() {}

    public Model(String id, String firstname, String lastname, String score){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.score = score;
    }
}
