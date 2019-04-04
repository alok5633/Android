
package com.example.phonebook;

import android.widget.ImageView;

public class ContactModel {


    private String Contact_name;
    private String Contact_no;
    private int logo;
    private int update;
    private  int delete;
    private int id;

    ContactModel(){

    }

    public ContactModel(int logo,String contact_name,String contact_no,int update,int delete) {

        Contact_name = contact_name;
        Contact_no = contact_no;
        this.logo=logo;
        this.update=update;
        this.delete=delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getContact_name() {
        return Contact_name;
    }

    public void setContact_name(String contact_name) {
        Contact_name = contact_name;
    }

    public String getContact_no() {
        return Contact_no;
    }

    public void setContact_no(String contact_no) {
        Contact_no = contact_no;
    }
}
