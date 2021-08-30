package com.mobile.bookingpitch_user.model.HistoryPitch_Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("listPitchsCancel")
@Expose
private List<ListPitchsCancel> listPitchsCancel = null;
@SerializedName("listPitchsWaiting")
@Expose
private List<ListPitchsWaiting> listPitchsWaiting = null;
@SerializedName("listPitchsKeeping")
@Expose
private List<ListPitchsKeeping> listPitchsKeeping = null;

public List<ListPitchsCancel> getListPitchsCancel() {
return listPitchsCancel;
}

public void setListPitchsCancel(List<ListPitchsCancel> listPitchsCancel) {
this.listPitchsCancel = listPitchsCancel;
}

public List<ListPitchsWaiting> getListPitchsWaiting() {
return listPitchsWaiting;
}

public void setListPitchsWaiting(List<ListPitchsWaiting> listPitchsWaiting) {
this.listPitchsWaiting = listPitchsWaiting;
}

public List<ListPitchsKeeping> getListPitchsKeeping() {
return listPitchsKeeping;
}

public void setListPitchsKeeping(List<ListPitchsKeeping> listPitchsKeeping) {
this.listPitchsKeeping = listPitchsKeeping;
}

}

