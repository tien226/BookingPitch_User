package com.mobile.bookingpitch_user.model.ForgotPass_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("result")
@Expose
private Boolean result;
@SerializedName("message")
@Expose
private String message;
@SerializedName("user_info")
@Expose
private UserInfo userInfo;

public Boolean getResult() {
return result;
}

public void setResult(Boolean result) {
this.result = result;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public UserInfo getUserInfo() {
return userInfo;
}

public void setUserInfo(UserInfo userInfo) {
this.userInfo = userInfo;
}

}

