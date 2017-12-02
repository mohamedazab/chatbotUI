package com.example.moham.chatbotui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Animation l = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome);
        iv.startAnimation(l);

        l.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Thread t = new Thread(){
                    public void run(){
                        try{
                            sleep(2000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
                };
                t.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
