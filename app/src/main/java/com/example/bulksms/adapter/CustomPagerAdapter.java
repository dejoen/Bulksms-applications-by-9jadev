package com.example.bulksms.adapter;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bulksms.R;
import com.example.bulksms.models.CustomViewModel;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<CustomViewModel> pager;

    public CustomPagerAdapter(Context context, ArrayList<CustomViewModel> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup v=(ViewGroup) LayoutInflater.from(context).inflate(R.layout.pageritem,container,false);
        ImageView imageView=v.findViewById(R.id.imageItem);
        TextView textView=v.findViewById(R.id.textme);
        imageView.setBackgroundResource(pager.get(position).getLayoutImage());
        textView.setText(pager.get(position).getDescription());
    /*    AnimationDrawable animationDrawable=(AnimationDrawable) textView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.start();*/
         container.addView(v);
         return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View)object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
