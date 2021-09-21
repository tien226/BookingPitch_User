package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.QuantityListener;
import com.mobile.bookingpitch_user.model.DateMonth;

import java.util.ArrayList;
import java.util.List;


public class DateMonth_Adapter extends RecyclerView.Adapter<DateMonth_Adapter.Viewholder> {
    private Context context;
    private List<DateMonth> dateMonthList;
    private List<DateMonth> dateMonthChose = new ArrayList<>();
    private QuantityListener quantityListener;

    public DateMonth_Adapter(Context context, List<DateMonth> dateMonthList, QuantityListener quantityListener) {
        this.context = context;
        this.dateMonthList = dateMonthList;
        this.quantityListener = quantityListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date_month, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.tvDateMonth.setText(dateMonthList.get(position).getDate());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    dateMonthChose.add(dateMonthList.get(position));
                }else {
                    dateMonthChose.remove(dateMonthList.get(position));
                }
                quantityListener.onQuantityChange(dateMonthChose);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateMonthList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvDateMonth;
        private CheckBox checkBox;

        public Viewholder(@NonNull  View itemView) {
            super(itemView);

            tvDateMonth = itemView.findViewById(R.id.tvdateMonth_Adapter);
            checkBox = itemView.findViewById(R.id.checkbox_Adapter);
        }
    }
}
