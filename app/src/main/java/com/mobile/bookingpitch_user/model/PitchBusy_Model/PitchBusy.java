package com.mobile.bookingpitch_user.model.PitchBusy_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PitchBusy {

    @SerializedName("dataMonth")
    @Expose
    private List<DataMonth> dataMonth = null;

    public List<DataMonth> getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(List<DataMonth> dataMonth) {
        this.dataMonth = dataMonth;
    }

}
