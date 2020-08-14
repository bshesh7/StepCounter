package com.example.bisheshstepcounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SensorListener extends Service implements SensorEventListener {
    @Override
    public void onSensorChanged(final SensorEvent event) {
         SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.bisheshstepcounter", Context.MODE_PRIVATE);

        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentDate = "dass";


        if(!sharedPreferences.contains(currentDate)){
            sharedPreferences.edit().putInt(currentDate,0).apply();
        }

        if(sharedPreferences.contains(currentDate)){
            int valu = sharedPreferences.getInt(currentDate,1);
            valu = valu + 1;
            sharedPreferences.edit().putInt(currentDate,valu).apply();
        }
    }

    public void onTaskRemoved(final Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        //if (BuildConfig.DEBUG) Logger.log("sensor service task removed");
        Toast.makeText(this,"sensor service task removed! ",Toast.LENGTH_SHORT).show();
        // Restart service in 500 ms
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                .set(AlarmManager.RTC, System.currentTimeMillis() + 500, PendingIntent
                        .getService(this, 3, new Intent(this, SensorListener.class), 0));
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
