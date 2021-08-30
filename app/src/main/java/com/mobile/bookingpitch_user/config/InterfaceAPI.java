package com.mobile.bookingpitch_user.config;

import com.mobile.bookingpitch_user.model.CancelPitch;
import com.mobile.bookingpitch_user.model.ChangePass_Model.ChangePass;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDCity;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDistrict;
import com.mobile.bookingpitch_user.model.DetailsPitch;
import com.mobile.bookingpitch_user.model.EmailForgotPass_Model.EmailForgotPass;
import com.mobile.bookingpitch_user.model.ForgotPass_Model.ForgotPass;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;
import com.mobile.bookingpitch_user.model.Login.ProfileEmployee;
import com.mobile.bookingpitch_user.model.News_Model.News;
import com.mobile.bookingpitch_user.model.PitchBusy_Model.PitchBusy;
import com.mobile.bookingpitch_user.model.PutPitch;
import com.mobile.bookingpitch_user.model.RegisterTwo_Model.RegisterTwo;
import com.mobile.bookingpitch_user.model.Register_Model.Register;
import com.mobile.bookingpitch_user.model.YardList_Model.YardList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceAPI {
    // api tinh thanh
    @GET("city.php")
    Call<HDCity> getCity();

    // api quan huyen
    @GET("district.php")
    Call<HDDistrict> getCityAndDistrict();

    //api dang ky 1
    @FormUrlEncoded
    @POST("register_ntd.php")
    Call<Register> registerAcc(@Field("name_ntd") String name_ntd,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("repass") String repass);

    //api dang ky 2
    @FormUrlEncoded
    @POST("register_ntd2.php")
    Call<RegisterTwo> registerAccTwo(@Field("token") String token,
                                     @Field("avatar") String avatar,
                                     @Field("telephone") String telephone,
                                     @Field("address") String address,
                                     @Field("city") String city,
                                     @Field("district") String district);

    // api login
    @FormUrlEncoded
    @POST("login_ntd.php")
    Call<ProfileEmployee> loginAccount(@FieldMap Map<String, String> params);

    // api lay danh sach san
    @GET("pitch/getAllU")
    Call<YardList> getAllPitch();

    // api lay thong tin
    @GET("news/getAllNews")
    Call<News> getAllNews();

    // api lấy thông tin sân đã đặt
    @GET("pitch/getByUser/{userID}")
    Call<HistoryPitch> getAllMyPitch(@Path("userID") String userID);

    // api change pass
    @FormUrlEncoded
    @POST("change_pass.php")
    Call<ChangePass> changePass(@Field("token") String token,
                                @Field("password") String password,
                                @Field("newpass") String newpass,
                                @Field("repass") String repass);

    // api email forgot pass
    @FormUrlEncoded
    @POST("email_forget_pass.php")
    Call<EmailForgotPass> emailForgotPass(@Field("email") String email,
                                          @Field("type") int type);

    // api forgot pass
    @FormUrlEncoded
    @POST("reset_pass.php")
    Call<ForgotPass> forgotPass(@Field("token") String token,
                                @Field("password") String password,
                                @Field("repass") String repass,
                                @Field("type") int type);

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
    Call<CancelPitch> cancelBookPitch(@Path("id") String id, @Field("state") String state);

    // api xem danh sách lịch sân bận
    @GET("pitch/getPitchBusy/{pitchID}")
    Call<PitchBusy> getAllPitchBusy(@Path("pitchID") String pitchID);


}