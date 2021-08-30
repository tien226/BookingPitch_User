package com.mobile.bookingpitch_user.model.ChangePass_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePass {

@SerializedName("data")
@Expose
private Data data;
@SerializedName("error")
@Expose
private Error error;

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

public Error getError() {
return error;
}

public void setError(Error error) {
this.error = error;
}

}


