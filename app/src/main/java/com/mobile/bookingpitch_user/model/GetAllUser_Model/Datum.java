package com.mobile.bookingpitch_user.model.GetAllUser_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

@SerializedName("userID")
@Expose
private String userID;
@SerializedName("userName")
@Expose
private String userName;
@SerializedName("quantityCancel")
@Expose
private Integer quantityCancel;
@SerializedName("quantityWating")
@Expose
private Integer quantityWating;
@SerializedName("quantityKeeping")
@Expose
private Integer quantityKeeping;
@SerializedName("quantityDone")
@Expose
private Integer quantityDone;

public String getUserID() {
return userID;
}

public void setUserID(String userID) {
this.userID = userID;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public Integer getQuantityCancel() {
return quantityCancel;
}

public void setQuantityCancel(Integer quantityCancel) {
this.quantityCancel = quantityCancel;
}

public Integer getQuantityWating() {
return quantityWating;
}

public void setQuantityWating(Integer quantityWating) {
this.quantityWating = quantityWating;
}

public Integer getQuantityKeeping() {
return quantityKeeping;
}

public void setQuantityKeeping(Integer quantityKeeping) {
this.quantityKeeping = quantityKeeping;
}

public Integer getQuantityDone() {
return quantityDone;
}

public void setQuantityDone(Integer quantityDone) {
this.quantityDone = quantityDone;
}

}

