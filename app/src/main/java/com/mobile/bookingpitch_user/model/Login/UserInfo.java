package com.mobile.bookingpitch_user.model.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

@SerializedName("ep_id")
@Expose
private String epId;
@SerializedName("ep_email")
@Expose
private String epEmail;
@SerializedName("ep_name")
@Expose
private String epName;
@SerializedName("ep_avatar")
@Expose
private String epAvatar;

public String getEpId() {
return epId;
}

public void setEpId(String epId) {
this.epId = epId;
}

public String getEpEmail() {
return epEmail;
}

public void setEpEmail(String epEmail) {
this.epEmail = epEmail;
}

public String getEpName() {
return epName;
}

public void setEpName(String epName) {
this.epName = epName;
}

public String getEpAvatar() {
return epAvatar;
}

public void setEpAvatar(String epAvatar) {
this.epAvatar = epAvatar;
}

}
