package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.DetaiilsPitch_Save_Activity;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsKeeping;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PitchKeeping_Adapter extends RecyclerView.Adapter<PitchKeeping_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ListPitchsKeeping> listPitchsKeepings;

    public PitchKeeping_Adapter(Context context, ArrayList<ListPitchsKeeping> listPitchsKeepings) {
        this.context = context;
        this.listPitchsKeepings = listPitchsKeepings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch_kepping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchKeeping_Adapter.ViewHolder holder, int position) {
        ListPitchsKeeping pitchsKeeping = listPitchsKeepings.get(position);

        String strUrl = "https://datn-2021.herokuapp.com" + pitchsKeeping.getImage();
//        String strUrl = pitchsKeeping.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.img_yardlist).error(R.drawable.img_yardlist).into(holder.imgAvatar);
        holder.tvName.setText(pitchsKeeping.getPitchName());
//        holder.tvType.setText(datum.getPitchName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        holder.tvPrime.setText(format.format(Integer.valueOf(pitchsKeeping.getPrice())) + " đ");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaiilsPitch_Save_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", strUrl);
                bundle.putString("name", pitchsKeeping.getPitchName());
                bundle.putString("price", pitchsKeeping.getPrice());
                bundle.putString("span", pitchsKeeping.getSpan());
                bundle.putString("note", pitchsKeeping.getDetail());
                bundle.putString("_Id", pitchsKeeping.getId());
                bundle.putString("userID", pitchsKeeping.getUserID());
                bundle.putString("totalPrice", pitchsKeeping.getTotalPrice());
                bundle.putBoolean("umpire", pitchsKeeping.getUmpire()); //trong tai
                bundle.putBoolean("tshirt", pitchsKeeping.getTshirt()); //ao dau
                bundle.putString("priceWater", pitchsKeeping.getPriceWater());
                bundle.putString("quantityWater", pitchsKeeping.getQuantityWater());
                bundle.putString("date", pitchsKeeping.getDate());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //
//        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
//                retrofit.create(InterfaceAPI.class)
//                        .cancelBookPitch(pitchsKeeping.getId(), "-1")
//                        .enqueue(new Callback<CancelPitch>() {
//                            @Override
//                            public void onResponse(Call<CancelPitch> call, Response<CancelPitch> response) {
//                                CancelPitch cancelPitch = response.body();
//                                if (cancelPitch.getSuccess() == true){
//
//                                    listPitchsKeepings.remove(position);
//                                    notifyItemRemoved(position);
//
//                                    Toast.makeText(context, cancelPitch.getMessage(), Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(context, "lỗi", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<CancelPitch> call, Throwable t) {
//                                Toast.makeText(context, "Lỗi mạng hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listPitchsKeepings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgEdit;
        private TextView tvName, tvType, tvPrime, tvStatus;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            imgEdit = itemView.findViewById(R.id.imgEdit_ItemPitchKeeping);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemPitchKeeping);
            tvName = itemView.findViewById(R.id.tvNamePitch_ItemPitchKeeping);
            tvType = itemView.findViewById(R.id.tvTyoePitch_ItemPitchKeeping);
            tvPrime = itemView.findViewById(R.id.tvPrimePitch_ItemPitchKeeping);
            layout = itemView.findViewById(R.id.llLayput_ItemPitchKeeping);
        }
    }
}
