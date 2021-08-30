package com.mobile.bookingpitch_user.model.PitchBusy_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataMonth {

@SerializedName("date")
@Expose
private String date;
@SerializedName("spanBusy")
@Expose
private List<String> spanBusy = null;

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public List<String> getSpanBusy() {
return spanBusy;
}

public void setSpanBusy(List<String> spanBusy) {
this.spanBusy = spanBusy;
}

}

