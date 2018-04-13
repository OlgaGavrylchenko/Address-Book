/*
 *   CIT243-H1
 *   Project - Address Book
 *   @Created by Olga Gavrylchenko, 04/10/2018
 * */
package com.example.android.myaddressbook;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ContactData implements Serializable, Comparable<Object>{

    private int contactID;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactStreet;
    private String contactCity;
    private String contactState;
    private String contactZip;

    public ContactData(){
        setContactID(-1);
        setContactName("");
        setContactPhone("");
        setContactEmail("");
        setContactStreet("");
        setContactState("");
        setContactZip("");
    }

    //this constructor is used for data from database
    public ContactData(int id, String name, String phone,
                        String email, String street, String city, String state, String zip){
        setContactID(id);
        setContactName(name);
        setContactPhone(phone);
        setContactEmail(email);
        setContactStreet(street);
        setContactCity(city);
        setContactState(state);
        setContactZip(zip);
    }

    public ContactData(String name, String phone,
                       String email, String street, String city, String state, String zip){
        setContactName(name);
        setContactPhone(phone);
        setContactEmail(email);
        setContactStreet(street);
        setContactCity(city);
        setContactState(state);
        setContactZip(zip);
    }

    public int getContactID(){
        return this.contactID;
    }

    public String getContactName(){
        return this.contactName;
    }

    public String getContactPhone(){
        return this.contactPhone;
    }

    public String getContactEmail(){
        return this.contactEmail;
    }

    public String getContactStreet(){
        return this.contactStreet;
    }

    public String getContactCity(){
        return this.contactCity;
    }

    public String getContactState(){
        return this.contactState;
    }

    public String getContactZip(){
        return this.contactZip;
    }

    public void setContactID(int id){
        this.contactID = id;
    }

    public void setContactName(String name){
        this.contactName = name;
    }

    public void setContactPhone(String phone){
        this.contactPhone = phone;
    }

    public void setContactEmail(String email){
        this.contactEmail = email;
    }

    public void setContactStreet(String street){
        this.contactStreet = street;
    }

    public void setContactCity(String city){
        this.contactCity = city;
    }

    public void setContactState(String state){
        this.contactState = state;
    }

    public void setContactZip(String zip){
        this.contactZip = zip;
    }

    public String toString(){

        StringBuffer str = new StringBuffer();

        if(!contactName.equals("")){
            str.append("Name: ");
            str.append(contactName).append("\n");
        }

        if(!contactPhone.equals("")){
            str.append("Phone: ");
            str.append(contactPhone).append("\n");
        }

        if(!contactEmail.equals("")){
            str.append("Email: ");
            str.append(contactEmail).append("\n");
        }

        if(!contactStreet.equals("")){
            str.append("Street: ");
            str.append(contactStreet).append("\n");
        }

        if(!contactCity.equals("")){
            str.append("City: ");
            str.append(contactCity).append("\n");
        }

        if(!contactState.equals("")){
            str.append("State: ");
            str.append(contactState).append("\n");
        }

        if(!contactZip.equals("")){
            str.append("Zip: ");
            str.append(contactZip).append("\n");
        }

        return str.toString();
    }

    @Override
    public int compareTo(Object o) {

        String name1 = ((ContactData) o).getContactName();

        String name2 = this.getContactName();

        return name2.compareTo(name1);
    }
}
