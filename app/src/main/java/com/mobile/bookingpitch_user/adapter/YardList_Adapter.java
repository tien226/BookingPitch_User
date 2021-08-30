package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.DetailsPitchActivity;
import com.mobile.bookingpitch_user.model.YardList_Model.Datum;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class YardList_Adapter extends RecyclerView.Adapter<YardList_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Datum> datumArrayList;
    public static String strName, strPrice, strTime, strDetail, strImg, strPitchId, strPriceWater;

    public YardList_Adapter(Context context, ArrayList<Datum> datumArrayList) {
        this.context = context;
        this.datumArrayList = datumArrayList;
    }

    public void setDataChange(ArrayList<Datum> newsList) {
        this.datumArrayList = newsList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public YardList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yardlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YardList_Adapter.ViewHolder holder, int position) {
        Datum datum = datumArrayList.get(position);

        String strUrl = "https://datn-2021.herokuapp.com" + datum.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.img_yardlist).error(R.drawable.img_yardlist).into(holder.imgAvatar);
        holder.tvName.setText(datum.getPitchName());
//        holder.tvType.setText(yardList.);
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        holder.tvPrime.setText(format.format(Integer.valueOf(datum.getPrice())) + " đ");
//        holder.tvPrime.setText(datum.getPrice() + " đ");

//        holder.tvStatus.setText(yardList);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsPitchActivity.class);
                strImg = strUrl;
                strName = datum.getPitchName();
                strPrice = datum.getPrice();
                strTime = datum.getSpan();
                strDetail = datum.getDetail();
                strPitchId = datum.getPitchID();
                strPriceWater = datum.getPriceWater();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName, tvType, tvPrime, tvStatus;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.llLayout_ItemYardList);
            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemYardList);
            tvName = itemView.findViewById(R.id.tvNamePitch_ItemYardList);
            tvType = itemView.findViewById(R.id.tvTypePitch_ItemYardList);
            tvPrime = itemView.findViewById(R.id.tvPrimePitch_ItemYardList);
//            tvStatus = itemView.findViewById(R.id.tvStatusPitch_ItemYardList);
        }
    }
}
