package com.nrk4220.android.bdrt;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    Animation animationFadeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ImageView background1 = (ImageView) findViewById(R.id.background1);
        final ImageView background2 = (ImageView) findViewById(R.id.background2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = background1.getWidth();
                //final float height = background1.getHeight();
                final float translationX = width * progress;
                //final float translationY = height * progress;
                background1.setTranslationX(translationX);
                background2.setTranslationX(translationX - width);
                //background1.setTranslationY(translationY);
                //background2.setTranslationY(translationY - width);
            }
        });
        animator.start();

        CountDownTimer logoCountdown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                animationFadeIn =  AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in_anim);

                final Button songList = (Button) findViewById(R.id.songList);
                final Button setting = (Button) findViewById(R.id.setting);

                songList.setVisibility(View.VISIBLE);
                setting.setVisibility(View.VISIBLE);

                songList.startAnimation(animationFadeIn);
                setting.startAnimation(animationFadeIn);

                songList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SplashScreen.this, SongList.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                });

                /*setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SplashScreen.this, Setting.class);
                        startActivity(intent);
                    }
                });*/

            }
        };
        logoCountdown.start();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
