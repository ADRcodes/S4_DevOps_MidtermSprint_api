package com.keyin.rest.user;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role = "USER";
    private String userTag;

    // This will allow us to store multiple tags for a user //
    @ElementCollection
    @CollectionTable(name = "preferred_tags", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "tag")
    private List<String> preferredTags = new ArrayList<>();


    // Constructor //
    public User(long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    // Temporary 3 param constructor
    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userTag = userTag;
        this.password = "";
        this.role = "";
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

    // Preferred tag methods //
    public List<String> getPreferredTags() {
        return preferredTags;
    }

    public void setPreferredTags(List<String> preferredTags) {

        if (preferredTags == null) {
            this.preferredTags = new ArrayList<>();
        } else {
            this.preferredTags = preferredTags;
        }
    }

    public void addPreferredTag(String tag) {
        if (tag == null || tag.isBlank()) return;
        if (preferredTags == null) preferredTags = new ArrayList<>();
        if (!preferredTags.contains(tag)) preferredTags.add(tag);
    }

    public void removePreferredTag(String tag) {
        if (preferredTags != null) preferredTags.remove(tag);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    // toString method//

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
    }

}
