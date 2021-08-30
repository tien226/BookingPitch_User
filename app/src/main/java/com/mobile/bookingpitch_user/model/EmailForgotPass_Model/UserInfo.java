package com.mobile.bookingpitch_user.model.EmailForgotPass_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("ep_email")
    @Expose
    private String epEmail;

    public String getEpEmail() {
        return epEmail;
    }

    public void setEpEmail(String epEmail) {
        this.epEmail = epEmail;
    }

}
