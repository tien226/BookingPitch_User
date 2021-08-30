package com.mobile.bookingpitch_user.model.ForgotPass_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

@SerializedName("ep_password")
@Expose
private String epPassword;

public String getEpPassword() {
return epPassword;
}

public void setEpPassword(String epPassword) {
this.epPassword = epPassword;
}

}
