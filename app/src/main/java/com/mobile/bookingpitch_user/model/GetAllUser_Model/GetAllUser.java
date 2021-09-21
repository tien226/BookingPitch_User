package com.mobile.bookingpitch_user.model.GetAllUser_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllUser {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("quantityUser")
    @Expose
    private Integer quantityUser;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getQuantityUser() {
        return quantityUser;
    }

    public void setQuantityUser(Integer quantityUser) {
        this.quantityUser = quantityUser;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
