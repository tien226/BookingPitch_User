package com.mobile.bookingpitch_user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.OnDistrictClick;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataDistrict;

import java.util.ArrayList;
import java.util.List;

public class DistrictChooserAdapter extends RecyclerView.Adapter<DistrictChooserAdapter.Holder> {

    private Context mContext;
    private List<HDDataDistrict> listDistrict = new ArrayList<>();
    private OnDistrictClick onDistrictClick;

    public DistrictChooserAdapter(Context context, List<HDDataDistrict> listDistrict) {
        this.mContext = context;
        this.listDistrict.addAll(listDistrict);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_city, parent, false);
        return new Holder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvCity.setText(listDistrict.get(position).getNameCity());
        holder.tvCity.setOnClickListener(v -> {
            onDistrictClick.onClick(listDistrict.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listDistrict.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvCity;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvCity = itemView.findViewById(R.id.tv_city);

        }

        private Context getContext() {
            return mContext;
        }
    }

    public void setOnclick(OnDistrictClick onclick) {
        this.onDistrictClick = onclick;
    }
}
