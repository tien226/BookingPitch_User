package com.mobile.bookingpitch_user.model.EmailForgotPass_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailForgotPass {

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
