package com.keyin.rest.user;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String userTag;

    // Constructor //
    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userTag = userTag;
    }

    // No-args constructor //
    public User() {

    }

    // Getters and Setters //
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserTag() { return userTag ; }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    // toString method//

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
    }

}
