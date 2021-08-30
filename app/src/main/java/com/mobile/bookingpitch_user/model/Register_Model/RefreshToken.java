package com.mobile.bookingpitch_user.model.Register_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {

@SerializedName("id_tmp")
@Expose
private String idTmp;
@SerializedName("ntd_email")
@Expose
private String ntdEmail;
@SerializedName("ntd_name")
@Expose
private String ntdName;
@SerializedName("ntd_password")
@Expose
private String ntdPassword;

public String getIdTmp() {
return idTmp;
}

public void setIdTmp(String idTmp) {
this.idTmp = idTmp;
}

public String getNtdEmail() {
return ntdEmail;
}

public void setNtdEmail(String ntdEmail) {
this.ntdEmail = ntdEmail;
}

public String getNtdName() {
return ntdName;
}

public void setNtdName(String ntdName) {
this.ntdName = ntdName;
}

public String getNtdPassword() {
return ntdPassword;
}

public void setNtdPassword(String ntdPassword) {
this.ntdPassword = ntdPassword;
}

}
