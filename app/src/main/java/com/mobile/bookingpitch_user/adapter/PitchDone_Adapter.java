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
import com.mobile.bookingpitch_user.model.HistoryPitch_Done.ListPitchsDone;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PitchDone_Adapter extends RecyclerView.Adapter<PitchDone_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ListPitchsDone> listPitchsDones;

    public PitchDone_Adapter(Context context, ArrayList<ListPitchsDone> listPitchsDones) {
        this.context = context;
        this.listPitchsDones = listPitchsDones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch_done, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListPitchsDone pitchsDone = listPitchsDones.get(position);

        String strUrl = "https://datn-2021.herokuapp.com" + pitchsDone.getImage();
//        String strUrl = pitchsKeeping.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.img_yardlist).error(R.drawable.img_yardlist).into(holder.imgAvatar);
        holder.tvName.setText(pitchsDone.getPitchName());
//        holder.tvType.setText(datum.getPitchName());
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        holder.tvPrime.setText(format.format(Integer.valueOf(pitchsDone.getPrice())) + " đ");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaiilsPitch_Save_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", strUrl);
                bundle.putString("name", pitchsDone.getPitchName());
                bundle.putString("price", pitchsDone.getPrice());
                bundle.putString("span", pitchsDone.getSpan());
                bundle.putString("note", pitchsDone.getDetail());
                bundle.putString("_Id", pitchsDone.getId());
                bundle.putString("userID", pitchsDone.getUserID());
                bundle.putString("totalPrice", pitchsDone.getTotalPrice());
                bundle.putBoolean("umpire", pitchsDone.getUmpire()); //trong tai
                bundle.putBoolean("tshirt", pitchsDone.getTshirt()); //ao dau
                bundle.putString("priceWater", pitchsDone.getPriceWater());
                bundle.putString("quantityWater", pitchsDone.getQuantityWater());
                bundle.putString("date", pitchsDone.getDate());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPitchsDones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgEdit;
        private TextView tvName, tvType, tvPrime, tvStatus;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemPitchDone);
            tvName = itemView.findViewById(R.id.tvNamePitch_ItemPitchDone);
            tvType = itemView.findViewById(R.id.tvTyoePitch_ItemPitchDone);
            tvPrime = itemView.findViewById(R.id.tvPrimePitch_ItemPitchDone);
            layout = itemView.findViewById(R.id.llLayput_ItemPitchDone);
        }
    }
}
