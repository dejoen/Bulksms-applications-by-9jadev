package com.example.bulksms.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bulksms.R;
import com.example.bulksms.adapter.CustomPagerAdapter;
import com.example.bulksms.fragments.SignInFragment;
import com.example.bulksms.models.CustomViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ViewPager viewPager;
View view;
FrameLayout frameLayout;
ArrayList<CustomViewModel> arrayList;
LinearLayout indicatorLayout;
TextView[] indicator;
ImageView moveNext;
private int mPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initializeViews();
       //testing
    }
    private void  initializeViews(){
        view=findViewById(R.id.view);
        frameLayout=findViewById(R.id.fram);
        viewPager=findViewById(R.id.myviewPager);
    indicatorLayout=findViewById(R.id.indicator);
    moveNext=findViewById(R.id.moveNext);
     arrayList=new ArrayList<>();
     arrayList.add(new CustomViewModel(R.drawable.bulksmso3,R.string.description));
        arrayList.add(new CustomViewModel(R.drawable.bulksmso3,R.string.description));
        arrayList.add(new CustomViewModel(R.drawable.bulksmso3,R.string.description));
        CustomPagerAdapter adapter =new CustomPagerAdapter(getApplicationContext(),arrayList);
         viewPager.setAdapter(adapter);
         viewPager.setPageMargin(20);
addIndicator(0);

         viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                 addIndicator(position);
              mPosition=position;
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
         moveNext.setOnClickListener(new View.OnClickListener() {
             int count=1;
             @Override
             public void onClick(View v) {

                 viewPager.setCurrentItem(getPosition()+1,true);
                 count++;
                 if(count==arrayList.size()+1){
                     view.setVisibility(View.GONE);
                     viewPager.setVisibility(View.GONE);
                     indicatorLayout.setVisibility(View.GONE);
                    moveNext.setVisibility(View.GONE);
                     frameLayout.setVisibility(View.VISIBLE);

                     addFragment(new SignInFragment());
                 }

             }
         });

    }
    private  void addIndicator(int pagerPos){
        indicator=new  TextView[arrayList.size()];
        indicatorLayout.removeAllViews();
        for(int i=0;i<indicator.length;i++){
            indicator[i]=new TextView(MainActivity.this);
            indicator[i].setText(Html.fromHtml("&#9673;"));
            indicator[i].setTextSize(35);
            indicator[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            indicatorLayout.addView(indicator[i]);
        }
        indicator[pagerPos].setTextColor(getResources().getColor(android.R.color.holo_orange_dark));

        Animation animationUtils=AnimationUtils.loadAnimation(MainActivity
        .this, android.R.anim.fade_out);
        indicator[pagerPos].setAnimation(animationUtils);


    }
    private  int getPosition(){
        return mPosition;
    }

    private  void addFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram,fragment);
        transaction.commit();

    }
}