package com.mobile.bookingpitch_user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.model.IntroHome;

import java.util.List;


public class sliderImgAdapter extends RecyclerView.Adapter<sliderImgAdapter.ViewHolder>{
    private List<IntroHome> introHomeList;
    private ViewPager2 viewPager2;

    public sliderImgAdapter(List<IntroHome> introHomeList, ViewPager2 viewPager2) {
        this.introHomeList = introHomeList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(introHomeList.get(position).getImage());
        if (position == introHomeList.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return introHomeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardView_SlideImage);
        }
        void  setImage(IntroHome introHome){
            imageView.setImageResource(introHome.getImage());
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            introHomeList.addAll(introHomeList);
            notifyDataSetChanged();
        }
    };
}
