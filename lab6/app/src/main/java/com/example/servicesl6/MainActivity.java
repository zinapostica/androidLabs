package com.example.servicesl6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Intent serviceIntent ;
    private FibService mService;
    private EditText input;
    private TextView result;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FibService.CustomBinder binder = (FibService.CustomBinder) service;
            mService = binder.getService();
            System.out.println("miauu");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.serviceIntent = new Intent(this, FibService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.result);

    }
    public void setResult(View v){
        long n = Long.parseLong(input.getText().toString());
        long res = mService.fib(n);
        result.setText(Long.toString(res));
    }
}

