package com.example.puzzle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private static ArrayList<PuzzleButton> buttons = new ArrayList<>();
    private GridView grid;
    private static ButtonAdapter adapter;
    public static ArrayList<PuzzleButton> getButtons() {
        return buttons;
    }

    class KeyListener implements View.OnKeyListener{

        @Override
        public boolean onKey( View view, int keyCode, KeyEvent evt ) {
            if (evt.getAction() == KeyEvent.ACTION_DOWN){
                int buttonPosition=0;
                for(int i=0;i<buttons.size();i++){
                    if(buttons.get(i).getId()==0){
                        buttonPosition=i;
                        break;
                    }
                    if(i==buttons.size()-1){
                        return false;
                    }
                }
                switch (evt.getKeyCode()){
                    case KeyEvent.KEYCODE_DPAD_DOWN :
                        if(buttonPosition<6){
                            Collections.swap(buttons,buttonPosition,buttonPosition+3);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP :
                        if(buttonPosition>2){
                            Collections.swap(buttons,buttonPosition,buttonPosition-3);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT :
                        if((buttonPosition%3)!=0){
                            Collections.swap(buttons,buttonPosition,buttonPosition-1);
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT :
                        if((buttonPosition%3)!=2){
                            Collections.swap(buttons,buttonPosition,buttonPosition+1);
                        }
                        break;
                }
                adapter.notifyDataSetChanged();
                if (isSorted()){
                    setContentView(R.layout.success);
                }
            }

            return true;
        }
    }
    public static boolean isSorted(){
        if(buttons.get(buttons.size()-1).getId()!=0){
            return false;
        }
        for(int i=0;i<buttons.size()-2;i++){
            if(buttons.get(i).getId()>buttons.get(i+1).getId()){
                return false;
            }
        }
        return true;
    }
    public void pozitioneazaButoane(){
        int imagini[] = {R.drawable.android,R.drawable.cinci,R.drawable.doi,R.drawable.unu,R.drawable.trei, R.drawable.patru,R.drawable.sapte,R.drawable.sase,R.drawable.opt};
        this.buttons.add(new PuzzleButton(imagini[0],0));
        for(int i=1;i<imagini.length;i++){
            this.buttons.add(new PuzzleButton(imagini[i],i));
        }
        Collections.shuffle(buttons);
    }
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pozitioneazaButoane();
        setContentView(R.layout.activity_main);
        grid = (GridView)findViewById(R.id.grid);
        adapter= new ButtonAdapter(getApplicationContext());
        grid.setAdapter(adapter);
        grid.setOnKeyListener(new KeyListener());

    }
}