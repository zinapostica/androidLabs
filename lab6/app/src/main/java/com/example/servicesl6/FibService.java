package com.example.servicesl6;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.security.Provider;

public class FibService extends Service {
    private CustomBinder myBinder=new CustomBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    long fib(long n)
    {
        long a = 0, b = 1, c;
        if (n == 0)
            return a;
        for (long i = 2; i <= n; i++)
        {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        this.stopSelf();
    }
    public class CustomBinder extends Binder{
        FibService getService(){
            return FibService.this;
        }
    }
}
