package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.BookNowPitchActivity;
import com.mobile.bookingpitch_user.activity.DetailsPitchActivity;
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.PitchBusy_Model.DataMonth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class
PitchBusy_Adapter extends RecyclerView.Adapter<PitchBusy_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DataMonth> dataMonthArrayList;
    private Dao dao;

    public PitchBusy_Adapter(Context context, ArrayList<DataMonth> dataMonthArrayList) {
        this.context = context;
        this.dataMonthArrayList = dataMonthArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pitch_busy, parent, false);

        dao = new Dao();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PitchBusy_Adapter.ViewHolder holder, int position) {
        DataMonth dataMonth = dataMonthArrayList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat outputSdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(dataMonth.getDate());
            holder.tvDate.setText(context.getString(R.string.ngay) + ": " + outputSdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvca.setText(dao.getSpanFromListID(context, dataMonth.getSpanBusy()));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPref_RC.getInstance_RC(context).isLoggedIn_RC()) {

                } else {
                    Intent intent = new Intent(context, BookNowPitchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", dataMonth.getDate());
                    bundle.putStringArrayList("spanBusy", (ArrayList<String>) dataMonth.getSpanBusy());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMonthArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvca;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate_ItemPitchBusy);
            tvca = itemView.findViewById(R.id.tvCa_ItemPitchBusy);
            layout = itemView.findViewById(R.id.layout_ItemPitchBusy);
        }
    }
}
