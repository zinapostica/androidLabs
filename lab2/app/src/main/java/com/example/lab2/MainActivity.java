package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    static public TextView text;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.text = (TextView) findViewById(R.id.textView);
        this.editButton = (Button) findViewById(R.id.button);
        final Intent editActivity = new Intent(getApplicationContext(), EditActivity.class);
        View.OnClickListener editButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(editActivity);
            }
        };
        editButton.setOnClickListener(editButtonOnClickListener);
    }
}