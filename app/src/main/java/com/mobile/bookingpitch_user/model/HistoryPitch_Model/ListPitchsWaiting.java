package com.mobile.bookingpitch_user.model.HistoryPitch_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPitchsWaiting {
    @SerializedName("pitchID")
    @Expose
    private String pitchID;
    @SerializedName("pitchName")
    @Expose
    private String pitchName;
    @SerializedName("span")
    @Expose
    private String span;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("umpire")
    @Expose
    private Boolean umpire;
    @SerializedName("quantityWater")
    @Expose
    private String quantityWater;
    @SerializedName("priceWater")
    @Expose
    private String priceWater;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tshirt")
    @Expose
    private Boolean tshirt;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("createBy")
    @Expose
    private String createBy;
    @SerializedName("codeSpecial")
    @Expose
    private String codeSpecial;
    @SerializedName("dayOfWeek")
    @Expose
    private String dayOfWeek;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getPitchID() {
        return pitchID;
    }

    public void setPitchID(String pitchID) {
        this.pitchID = pitchID;
    }

    public String getPitchName() {
        return pitchName;
    }

    public void setPitchName(String pitchName) {
        this.pitchName = pitchName;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getUmpire() {
        return umpire;
    }

    public void setUmpire(Boolean umpire) {
        this.umpire = umpire;
    }

    public String getQuantityWater() {
        return quantityWater;
    }

    public void setQuantityWater(String quantityWater) {
        this.quantityWater = quantityWater;
    }

    public String getPriceWater() {
        return priceWater;
    }

    public void setPriceWater(String priceWater) {
        this.priceWater = priceWater;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getTshirt() {
        return tshirt;
    }

    public void setTshirt(Boolean tshirt) {
        this.tshirt = tshirt;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCodeSpecial() {
        return codeSpecial;
    }

    public void setCodeSpecial(String codeSpecial) {
        this.codeSpecial = codeSpecial;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
