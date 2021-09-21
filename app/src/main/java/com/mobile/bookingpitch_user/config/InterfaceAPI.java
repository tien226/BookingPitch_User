package com.mobile.bookingpitch_user.config;

import com.mobile.bookingpitch_user.model.CancelPitch;
import com.mobile.bookingpitch_user.model.CreateUser;
import com.mobile.bookingpitch_user.model.DetailsPitch;
import com.mobile.bookingpitch_user.model.GetAllUser_Model.GetAllUser;
import com.mobile.bookingpitch_user.model.HistoryPitch_Done.HistoryDone;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;
import com.mobile.bookingpitch_user.model.News_Model.News;
import com.mobile.bookingpitch_user.model.PitchBusy_Model.PitchBusy;
import com.mobile.bookingpitch_user.model.PutPitch;
import com.mobile.bookingpitch_user.model.PutPitchMultiDate;
import com.mobile.bookingpitch_user.model.YardList_Model.YardList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceAPI {
    // api lay danh sach san
    @GET("pitch/getAllU")
    Call<YardList> getAllPitch();

    // api lay thong tin
    @GET("news/getAllNews")
    Call<News> getAllNews();

    // api lấy thông tin sân đã đặt
    @GET("pitch/getByUser/{userID}")
    Call<HistoryPitch> getAllMyPitch(@Path("userID") String userID);

    // api details pitch
    @FormUrlEncoded
    @POST("pitch/getDetailPitch")
    Call<DetailsPitch> detailsPitch(@FieldMap Map<String, String> params);

    // api đặt sân
    @FormUrlEncoded
    @POST("pitch/create")
    Call<PutPitch> putPitch(@FieldMap Map<String, String> params);

    // api huy đặt sân
    @FormUrlEncoded
    @POST("pitch/user/editPitch/{id}")
    Call<CancelPitch> cancelBookPitch(@Path("id") String id, @Field("state") String state, @Field("typeBooking") String typeBooking, @Field("editBy") String editBy);

    // api xem danh sách lịch sân bận
    @GET("pitch/getPitchBusy/{pitchID}")
    Call<PitchBusy> getAllPitchBusy(@Path("pitchID") String pitchID);

    // api xem danh sách ca bận theo ngày của tất cả các sân
    @GET("pitch/getPitchBusyByDate/{date}")
    Call<com.mobile.bookingpitch_user.model.PitchBusyByDate.PutPitch> getAllPitchBusyByDate(@Path("date") String date);

    // api thêm mới user
    @FormUrlEncoded
    @POST("user/createUser")
    Call<CreateUser> createUser(@FieldMap Map<String, String> maps);

    // api đặt sân theo tuần
    @FormUrlEncoded
    @POST("pitch/createAny")
    Call<PutPitchMultiDate> putPitchMultiDate(@FieldMap Map<String, String> maps, @FieldMap Map<String, List<String>> dateAny);

    // api lấy danh sách lịch sử đã đá
    @GET("user/getHistory/{userID}")
    Call<HistoryDone> getPitchDone(@Path("userID") String userID);

    // api lấy ds user
    @GET("user/getAll")
    Call<GetAllUser> getAllAccUser();

}