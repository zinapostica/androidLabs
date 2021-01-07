package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity{
    private EditText newText;
    private Button button;
    private TextView initialText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        button=(Button)findViewById(R.id.button2);
        button=(Button)findViewById(R.id.button2);
        newText=(EditText)findViewById(R.id.field);
        initialText=(TextView) findViewById(R.id.textView);
        View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.text.setText(newText.getText());
                EditActivity.this.onBackPressed();
            }
        };
        button.setOnClickListener(buttonOnClickListener);
    }
}