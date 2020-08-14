package com.example.bisheshstepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    TextView tv_steps_yesterday;

    SensorManager sensorManager;
    boolean running = false;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_steps = (TextView)findViewById(R.id.tv_steps);
        tv_steps_yesterday = (TextView)findViewById(R.id.tv_steps_yesterday);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String yesDate = "28-07-2020";


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sharedPreferences = this.getSharedPreferences("com.example.bisheshstepcounter",Context.MODE_PRIVATE);

        SharedPreferences.Editor ed;
        if(!sharedPreferences.contains(currentDate)){
            Toast.makeText(this,"doesnt contain the date",Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().putInt(currentDate,0).apply();
        }
        if(sharedPreferences.contains(currentDate)){
            int v = sharedPreferences.getInt(currentDate,1);
            tv_steps_yesterday.setText(String.valueOf(v));
        }

        int valu = sharedPreferences.getInt("date",1);
        Log.i("the log out value", String.valueOf(valu));
        //Toast.makeText(this,String.valueOf(valu),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this,countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this,"Sensor not found! ",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //sensorManager.unregisterListener(this);



    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // global variable  day today : 07/16/2020
        // if dayvar-currentday >=1 day == reset steps

        if(running){
            tv_steps.setText(String.valueOf(event.values[0]));

        }
        // I get the current date store it in currentdate
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if(sharedPreferences.contains(currentDate)){
            int valu = sharedPreferences.getInt(currentDate,1);
            valu = valu + 1;
            sharedPreferences.edit().putInt(currentDate,valu).apply();
            Toast.makeText(this,String.valueOf(valu),Toast.LENGTH_SHORT).show();
            tv_steps_yesterday.setText(String.valueOf(valu));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
