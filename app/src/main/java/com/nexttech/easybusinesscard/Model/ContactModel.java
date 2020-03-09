package com.nexttech.easybusinesscard.Model;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class ContactModel {
    private String name;
    private String email;
    private String contact;
    private String github;

    public ContactModel(String name, String email, String contact, String github) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.github = github;
    }

    public String getName() {
        return decodeText(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return decodeText(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return decodeText(contact);
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGithub() {
        return decodeText(github);
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String decodeText(String encodeText){
        byte[] data = new byte[0];
        try {
            data = Base64.decode(encodeText.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(data);
    }
}
