package com.example.l05_threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    class Flower{
         int image;
         String name;
        public Flower(int image, String name) {
            this.image = image;
            this.name = name;
        }
    }
    private Flower flowers[] = { new Flower(R.drawable.anamone, "Anamone"),
            new Flower(R.drawable.daffodil,"Daffodil"), new Flower(R.drawable.daisy,"Daisy"),
            new Flower(R.drawable.gladiolus,"Gladiolus"), new Flower(R.drawable.lilys,"Lilys"),
            new Flower(R.drawable.orchid,"Orchid"), new Flower(R.drawable.poppy, "Poppy"),
            new Flower(R.drawable.rose,"Rose"), new Flower(R.drawable.sunflower, "Sunflower"),
            new Flower(R.drawable.tulips,"Tulips"),
            new Flower(R.drawable.waterlily," Water Lily") };
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.flower_name);
        imageView = (ImageView) findViewById(R.id.flower_picture);
        textView.setText(flowers[0].name);
        imageView.setImageResource(flowers[0].image);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(flowers[currentIndex].name);
                            imageView.setImageResource(flowers[currentIndex].image);
                            if(currentIndex<flowers.length-1){
                                currentIndex++;
                            }else{
                                currentIndex=0;
                            }
                        }
                    });
                }
            }
        }).start();
    }
}