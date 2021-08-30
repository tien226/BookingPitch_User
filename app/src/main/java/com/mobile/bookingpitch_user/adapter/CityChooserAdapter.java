package com.mobile.bookingpitch_user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.OnCityClick;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataCity;

import java.util.ArrayList;
import java.util.List;

public class CityChooserAdapter extends RecyclerView.Adapter<CityChooserAdapter.Holder> {

    private Context mContext;
    private List<HDDataCity> listCity = new ArrayList<>();
    private HDDataCity HDDataCity;
    private OnCityClick onCityClick;

    public CityChooserAdapter(Context context, List<HDDataCity> listCity) {
        this.mContext = context;
        HDDataCity hdDataCity = new HDDataCity();
        hdDataCity.setIdCity("0");
        hdDataCity.setNameCity("");
        listCity.add(0,hdDataCity);
        this.listCity.addAll(listCity);
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
        holder.tvCity.setText(listCity.get(position).getNameCity().equals("") ? "<Trống>" : listCity.get(position).getNameCity());
//        holder.tvCity.setText(listCity.get(position).getNameCity());
        holder.tvCity.setOnClickListener(v -> {
            onCityClick.onClick(listCity.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }

    public void filterList(ArrayList<HDDataCity> filteredList) {
        listCity = filteredList;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvCity;
        LinearLayout linearLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            linearLayout = itemView.findViewById(R.id.layout_cardCity);
        }

        private Context getContext() {
            return mContext;
        }
    }

    public void setOnclick(OnCityClick onclick) {
        this.onCityClick = onclick;
    }


}
