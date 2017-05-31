package com.tanyaohotnik.moneycontrol.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.File;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class User extends SugarRecord {
   
    private String password;
    private String name;
    private String email;
    private File photo;

    public User() {
    }

    public long getUserId() {
        return this.getId().longValue();
    }

//    public void setUserId(long user_id) {
//        this.user_id = user_id;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
    public String getPhotoFilename(){
        return "IMG_" +  "USER";
    }
}
