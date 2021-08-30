package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.DetailsNewsActivity;
import com.mobile.bookingpitch_user.model.News_Model.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Datum> datumArrayList;

    public News_Adapter(Context context, ArrayList<Datum> datumArrayList) {
        this.context = context;
        this.datumArrayList = datumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ite_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_Adapter.ViewHolder holder, int position) {
        Datum datum = datumArrayList.get(position);

        String strUrl = "https://datn-2021.herokuapp.com" + datum.getImage();
        Picasso.get().load(strUrl).placeholder(R.drawable.image_news).error(R.drawable.image_news).into(holder.imgAvatar);
        holder.tvTitle.setText(datum.getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgNews", strUrl);
                bundle.putString("titleNews", datum.getTitle());
                bundle.putString("contentNews", datum.getContent());
                intent.putExtras(bundle);
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
        private TextView tvTitle;
        private LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imgAvatar_ItemNews);
            tvTitle = itemView.findViewById(R.id.tvTitle_ItemNews);
            linearLayout = itemView.findViewById(R.id.llLayout_ItemNews);
        }
    }
}
