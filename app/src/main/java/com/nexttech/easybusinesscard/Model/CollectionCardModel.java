package com.nexttech.easybusinesscard.Model;

public class CollectionCardModel {

    private String id;
    private String name;
    private String designation;
    private String project;
    private String companyName;
    private String email;
    private String phone;
    private String fax;
    private String mobile;
    private String website;
    private String address;
    private String cardTemplate;

    public CollectionCardModel() {
    }

    public CollectionCardModel(String name, String designation, String project, String companyName, String email, String phone, String fax, String mobile, String website, String address, String cardTemplate) {
        this.name = name;
        this.designation = designation;
        this.project = project;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.mobile = mobile;
        this.website = website;
        this.address = address;
        this.cardTemplate = cardTemplate;
    }

    public CollectionCardModel(String id, String name, String designation, String project, String companyName, String email, String phone, String fax, String mobile, String website, String address, String cardTemplate) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.project = project;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.mobile = mobile;
        this.website = website;
        this.address = address;
        this.cardTemplate = cardTemplate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardTemplate() {
        return cardTemplate;
    }

    public void setCardTemplate(String cardTemplate) {
        this.cardTemplate = cardTemplate;
    }
}