package com.mobile.bookingpitch_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.model.IntroHome;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private List<IntroHome> photoList;

    public PhotoAdapter(Context context, List<IntroHome> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_introhome,container,false);
        ImageView img_photo = view.findViewById(R.id.img_viewpager);

        IntroHome photo = photoList.get(position);
        if(photo != null){
            Glide.with(context).load(photo.getImage()).into(img_photo);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(photoList != null){
            return photoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull  Object object) {
        container.removeView((View) object);
    }
}
