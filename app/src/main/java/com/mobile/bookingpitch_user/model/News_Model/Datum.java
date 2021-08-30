package com.mobile.bookingpitch_user.model.News_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("title")
@Expose
private String title;
@SerializedName("content")
@Expose
private String content;
@SerializedName("image")
@Expose
private String image;
@SerializedName("_id")
@Expose
private String id;
@SerializedName("__v")
@Expose
private Integer v;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getContent() {
return content;
}

public void setContent(String content) {
this.content = content;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
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

