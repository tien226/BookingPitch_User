package com.mobile.bookingpitch_user.model.Register_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

@SerializedName("ep_name")
@Expose
private String epName;
@SerializedName("ep_email")
@Expose
private String epEmail;
@SerializedName("ep_pass")
@Expose
private String epPass;

public String getEpName() {
return epName;
}

public void setEpName(String epName) {
this.epName = epName;
}

public String getEpEmail() {
return epEmail;
}

public void setEpEmail(String epEmail) {
this.epEmail = epEmail;
}

public String getEpPass() {
return epPass;
}

public void setEpPass(String epPass) {
this.epPass = epPass;
}

}
