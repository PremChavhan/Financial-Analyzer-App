package com.androcrush.sqlite2;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.timqi.sectorprogressview.ColorfulRingProgressView;

public class DisplayCredit extends AppCompatActivity {
    ObjectAnimator animator;
    ColorfulRingProgressView crpv;
    TextView tvpercent;
    float startangle=-90;
    boolean flag=false;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_credit);
        Intent intent=getIntent();
        int cred=intent.getIntExtra("cred",0);
        tvpercent=findViewById(R.id.tvPercent);
        crpv = (ColorfulRingProgressView) findViewById(R.id.crpv);
        crpv.setPercent((cred)/10);
        tvpercent.setText("Credit Score:"+cred);
        crpv.setStartAngle(getStartAngle());
        //rpv.setFgColorStart(0xffffe400);
        //crpv.setFgColorEnd(0xffff4800);
//        crpv.setStrokeWidthDp(21);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            crpv.animateIndeterminate();
//            }
//        }, 3000);
//        crpv.stopAnimateIndeterminate();

        tvpercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==false)
                {
                    crpv.animateIndeterminate();
                    flag=true;
                }
                else
                {
                    crpv.stopAnimateIndeterminate();
                    flag=false;
                    //startangle=crpv.getStartAngle();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(DisplayCredit.this,CountryListActivity.class);
        startActivity(intent);
        finish();
    }

    private float getStartAngle() {
        return startangle;
    }

//    private void animate() {
//        animateIndeterminate(800, new AccelerateDecelerateInterpolator());
//    }
//
//    @SuppressLint("ObjectAnimatorBinding")
//    private void animateIndeterminate(int durationOneCircle,
//                                     TimeInterpolator interpolator) {
//        animator = ObjectAnimator.ofFloat(this, "startAngle", getStartAngle(), getStartAngle() + 360);
//        if (interpolator != null) animator.setInterpolator(interpolator);
//        animator.setDuration(durationOneCircle);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.start();
//    }

}