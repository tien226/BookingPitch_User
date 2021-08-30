package com.mobile.bookingpitch_user.model.RegisterTwo_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

@SerializedName("ep_id_ntd")
@Expose
private Integer epIdNtd;
@SerializedName("ep_avatar")
@Expose
private String epAvatar;
@SerializedName("ep_telephone")
@Expose
private String epTelephone;
@SerializedName("ep_address")
@Expose
private String epAddress;
@SerializedName("ep_city")
@Expose
private String epCity;
@SerializedName("ep_district")
@Expose
private String epDistrict;
@SerializedName("otp")
@Expose
private Integer otp;

public Integer getEpIdNtd() {
return epIdNtd;
}

public void setEpIdNtd(Integer epIdNtd) {
this.epIdNtd = epIdNtd;
}

public String getEpAvatar() {
return epAvatar;
}

public void setEpAvatar(String epAvatar) {
this.epAvatar = epAvatar;
}

public String getEpTelephone() {
return epTelephone;
}

public void setEpTelephone(String epTelephone) {
this.epTelephone = epTelephone;
}

public String getEpAddress() {
return epAddress;
}

public void setEpAddress(String epAddress) {
this.epAddress = epAddress;
}

public String getEpCity() {
return epCity;
}

public void setEpCity(String epCity) {
this.epCity = epCity;
}

public String getEpDistrict() {
return epDistrict;
}

public void setEpDistrict(String epDistrict) {
this.epDistrict = epDistrict;
}

public Integer getOtp() {
return otp;
}

public void setOtp(Integer otp) {
this.otp = otp;
}

}
