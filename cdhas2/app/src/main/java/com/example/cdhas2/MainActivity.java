package com.example.cdhas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private int tick=0;
    private boolean stopa=false;
    private Thread thread;
    private int b=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState!=null) {
            tick =savedInstanceState.getInt("tick");
            stopa=savedInstanceState.getBoolean("stopa");
            statrTick();
        }
    }

    public void starton(View view){
        stopa=false;
        b=0;
        if(thread==null){
            statrTick();
        }

    }

    private void statrTick() {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        if(!stopa){
                            tick=tick+1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int s=tick%60;
                    int m=tick/60%60;
                    int h=tick/60/60;
                    final String times=String.format("%02d:%02d:%02d",h,m,s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.textsj)).setText(times);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    public void stopon(View view) {
        stopa=true;
    }

    public void reset(View view) {
        stopa=true;
        tick=0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(b!=1)
            stopa=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(stopa)
        {
            b=1;
        }
        else
            stopa=true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tick",tick);
        outState.putBoolean("stopa",stopa);
    }
}