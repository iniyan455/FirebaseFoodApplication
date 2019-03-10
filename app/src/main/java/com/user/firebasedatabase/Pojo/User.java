package com.user.firebasedatabase.Pojo;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {


    public User() {
    }

    public String mobileno;
    public String email;
   private String typeofuser;
public String password;
    public String address;
    public String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeofuser() {
        return typeofuser;
    }

    public void setTypeofuser(String typeofuser) {
        this.typeofuser = typeofuser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String mobileno, String email, String typeofuser, String address, String name,String password) {

        this.mobileno = mobileno;
        this.email = email;
        this.typeofuser = typeofuser;
        this.address = address;
        this.name = name;
        this.password=password;
    }
}
