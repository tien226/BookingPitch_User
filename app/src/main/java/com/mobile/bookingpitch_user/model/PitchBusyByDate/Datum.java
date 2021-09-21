package com.mobile.bookingpitch_user.model.PitchBusyByDate;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("pitchName")
@Expose
private String pitchName;
@SerializedName("spanBusy")
@Expose
private List<String> spanBusy = null;

public String getPitchName() {
return pitchName;
}

public void setPitchName(String pitchName) {
this.pitchName = pitchName;
}

public List<String> getSpanBusy() {
return spanBusy;
}

public void setSpanBusy(List<String> spanBusy) {
this.spanBusy = spanBusy;
}

}
