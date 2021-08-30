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
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsCancel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PitchCancel_Adapter extends RecyclerView.Adapter<PitchCancel_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ListPitchsCancel> listPitchCancel;

    public PitchCancel_Adapter(Context context, ArrayList<ListPitchsCancel> listPitchCancel) {
        this.context = context;
        this.listPitchCancel = listPitchCancel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch_cancel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchCancel_Adapter.ViewHolder holder, int position) {
        ListPitchsCancel pitchsCancel = listPitchCancel.get(position);

//        String strUrl = "https://datn-2021.herokuapp.com" + pitchsCancel.getImage();
        String strUrl = pitchsCancel.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.img_yardlist).error(R.drawable.img_yardlist).into(holder.imgAvatar);
        holder.tvName.setText(pitchsCancel.getPitchName());
//        holder.tvType.setText(datum.getPitchName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        holder.tvPrime.setText(format.format(Integer.valueOf(pitchsCancel.getPrice())) + " đ");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaiilsPitch_Save_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", strUrl);
                bundle.putString("name", pitchsCancel.getPitchName());
                bundle.putString("price", pitchsCancel.getPrice());
                bundle.putString("span", pitchsCancel.getSpan());
                bundle.putString("note", pitchsCancel.getDetail());
                bundle.putString("_Id", pitchsCancel.getId());
                bundle.putString("userID", pitchsCancel.getUserID());
                bundle.putString("totalPrice", pitchsCancel.getTotalPrice());
                bundle.putBoolean("umpire", pitchsCancel.getUmpire()); //trong tai
                bundle.putBoolean("tshirt", pitchsCancel.getTshirt()); //ao dau
                bundle.putString("priceWater", pitchsCancel.getPriceWater());
                bundle.putString("quantityWater", pitchsCancel.getQuantityWater());
                bundle.putString("date", pitchsCancel.getDate());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPitchCancel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName, tvType, tvPrime, tvStatus;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemPitchCancel);
            tvName = itemView.findViewById(R.id.tvNamePitch_ItemPitchCancel);
            tvType = itemView.findViewById(R.id.tvTyoePitch_ItemPitchCancel);
            tvPrime = itemView.findViewById(R.id.tvPrimePitch_ItemPitchCancel);
            layout = itemView.findViewById(R.id.llLayput_ItemPitchCancel);

        }
    }
}
