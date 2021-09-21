package com.mobile.bookingpitch_user.model.HistoryPitch_Done;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryDone {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
