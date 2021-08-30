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
import com.mobile.bookingpitch_user.config.InterfaceClickDialog;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsWaiting;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PitchWaitting_Adapter extends RecyclerView.Adapter<PitchWaitting_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ListPitchsWaiting> listPitchsWaitings;
    private InterfaceClickDialog clickDialog;
    public static String _Id;

    public PitchWaitting_Adapter(Context context, ArrayList<ListPitchsWaiting> listPitchsWaitings, InterfaceClickDialog clickDialog) {
        this.context = context;
        this.listPitchsWaitings = listPitchsWaitings;
        this.clickDialog = clickDialog;
    }

    public void setDataChange(ArrayList<ListPitchsWaiting> listPitchsWaitings) {
        this.listPitchsWaitings = listPitchsWaitings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_save_pitch, parent, false);
        return new ViewHolder(view, clickDialog);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchWaitting_Adapter.ViewHolder holder, int position) {
        ListPitchsWaiting pitchsWaiting = listPitchsWaitings.get(position);

//        String strUrl = "https://datn-2021.herokuapp.com" + pitchsWaiting.getImage();
        String strUrl = pitchsWaiting.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.img_yardlist).error(R.drawable.img_yardlist).into(holder.imgAvatar);
        holder.tvName.setText(pitchsWaiting.getPitchName());
//        holder.tvType.setText(datum.getPitchName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        holder.tvPrime.setText(format.format(Integer.valueOf(pitchsWaiting.getPrice())) + " đ");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaiilsPitch_Save_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", strUrl);
                bundle.putString("name", pitchsWaiting.getPitchName());
                bundle.putString("price", pitchsWaiting.getPrice());
                bundle.putString("span", pitchsWaiting.getSpan());
                bundle.putString("note", pitchsWaiting.getDetail());
                bundle.putString("_Id", pitchsWaiting.getId());
                bundle.putString("userID", pitchsWaiting.getUserID());
                bundle.putString("totalPrice", pitchsWaiting.getTotalPrice());
                bundle.putBoolean("umpire", pitchsWaiting.getUmpire()); //trong tai
                bundle.putBoolean("tshirt", pitchsWaiting.getTshirt()); //ao dau
                bundle.putString("priceWater", pitchsWaiting.getPriceWater());
                bundle.putString("quantityWater", pitchsWaiting.getQuantityWater());
                bundle.putString("date", pitchsWaiting.getDate());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


        _Id = pitchsWaiting.getId();
//        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
//                retrofit.create(InterfaceAPI.class)
//                        .cancelBookPitch(pitchsWaiting.getId(), "-1")
//                        .enqueue(new Callback<CancelPitch>() {
//                            @Override
//                            public void onResponse(Call<CancelPitch> call, Response<CancelPitch> response) {
//                                CancelPitch cancelPitch = response.body();
//                                if (cancelPitch.getSuccess() == true){
//
//                                    listPitchsWaitings.remove(position);
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
        return listPitchsWaitings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgAvatar, imgEdit;
        private TextView tvName, tvType, tvPrime, tvStatus;
        private CardView layout;
        private InterfaceClickDialog interfaceClickDialog;

        public ViewHolder(@NonNull View itemView, InterfaceClickDialog interfaceClickDialog) {
            super(itemView);

            imgEdit = itemView.findViewById(R.id.imgEdit_ItemSavePitch);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemSavePitch);
            tvName = itemView.findViewById(R.id.tvNamePitch_ItemSavePitch);
            tvType = itemView.findViewById(R.id.tvTyoePitch_ItemSavePitch);
            tvPrime = itemView.findViewById(R.id.tvPrimePitch_ItemSavePitch);
            layout = itemView.findViewById(R.id.llLayput_ItemSavePitch);

            this.interfaceClickDialog = interfaceClickDialog;
            imgEdit.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            interfaceClickDialog.onClickDialog(getAdapterPosition());
        }
    }
}
