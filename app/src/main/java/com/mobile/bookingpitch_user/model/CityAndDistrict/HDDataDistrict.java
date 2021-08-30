package com.mobile.bookingpitch_user.model.CityAndDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HDDataDistrict implements Serializable {

    @SerializedName("id_city")
    @Expose
    private String idCity;
    @SerializedName("name_city")
    @Expose
    private String nameCity;
    @SerializedName("cit_parent")
    @Expose
    private String citParent;

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getCitParent() {
        return citParent;
    }

    public void setCitParent(String citParent) {
        this.citParent = citParent;
    }
}