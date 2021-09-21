package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.model.PitchBusyByDate.Datum;

import java.util.ArrayList;

public class PitchBusyByDate_Adapter extends RecyclerView.Adapter<PitchBusyByDate_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Datum> datumList;

    public PitchBusyByDate_Adapter(Context context, ArrayList<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public PitchBusyByDate_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch_busy_by_date, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchBusyByDate_Adapter.ViewHolder holder, int position) {
        Datum datum = datumList.get(position);
        holder.tvPitchName.setText(datum.getPitchName());

        for (int i = 0; i < datum.getSpanBusy().size(); i++) {
            if (datum.getSpanBusy().get(i) != null) {
                Log.e(PitchWaitting_Adapter.class.getName(), datum.getSpanBusy().get(i));
                if (datum.getSpanBusy().get(i).equals("1")) {
                    holder.tvCa1.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvCa1.setBackgroundResource(R.drawable.bg_full_color_red);
                }else {

                }
                if (datum.getSpanBusy().get(i).equals("2")) {
                    holder.tvCa2.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvCa2.setBackgroundResource(R.drawable.bg_full_color_red);
                }else {

                } if (datum.getSpanBusy().get(i).equals("3")) {
                    holder.tvCa3.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvCa3.setBackgroundResource(R.drawable.bg_full_color_red);
                }else {

                } if (datum.getSpanBusy().get(i).equals("4")) {
                    holder.tvCa4.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvCa4.setBackgroundResource(R.drawable.bg_full_color_red);
                }else {

                } if (datum.getSpanBusy().get(i).equals("5")) {
                    holder.tvCa5.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvCa5.setBackgroundResource(R.drawable.bg_full_color_red);
                }else {

                }
            }
        }

//        holder.tvSpanPitch.setText(new Dao().getSpanFromListID(context, datum.getSpanBusy()));
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPitchName, tvSpanPitch, tvCa1, tvCa2, tvCa3, tvCa4, tvCa5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPitchName = itemView.findViewById(R.id.tvPitchName_ItemPitchBusyByDate);
//            tvSpanPitch = itemView.findViewById(R.id.tvSpanPitch_ItemPitchBusyBudate);
            tvCa1 = itemView.findViewById(R.id.tvCa1_ItemPitchBusyBudate);
            tvCa2 = itemView.findViewById(R.id.tvCa2_ItemPitchBusyBudate);
            tvCa3 = itemView.findViewById(R.id.tvCa3_ItemPitchBusyBudate);
            tvCa4 = itemView.findViewById(R.id.tvCa4_ItemPitchBusyBudate);
            tvCa5 = itemView.findViewById(R.id.tvCa5_ItemPitchBusyBudate);

        }
    }
}
