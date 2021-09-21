package com.mobile.bookingpitch_user.model.HistoryPitch_Done;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("listPitchsCancel")
@Expose
private List<ListPitchsCancel> listPitchsCancel = null;
@SerializedName("listPitchsDone")
@Expose
private List<ListPitchsDone> listPitchsDone = null;

public List<ListPitchsCancel> getListPitchsCancel() {
return listPitchsCancel;
}

public void setListPitchsCancel(List<ListPitchsCancel> listPitchsCancel) {
this.listPitchsCancel = listPitchsCancel;
}

public List<ListPitchsDone> getListPitchsDone() {
return listPitchsDone;
}

public void setListPitchsDone(List<ListPitchsDone> listPitchsDone) {
this.listPitchsDone = listPitchsDone;
}

}
