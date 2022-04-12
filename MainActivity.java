/*Hi 
This code is written in ANDROID STUDIO



JAVA CODE
*/






package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageview;
    private ImageView imageview2;
    private SensorManager sensormanager;
    private Sensor accelerometerSensor;
    private boolean isavailable;
    private float currentXcordinate , currentYcordinate, currentZcordinate, lastXcordinate, lastYcordinate, lastZcordinate, xDifference, yDifference, zDifference,threshold=5f;
    private boolean isnotfirst=false;
    private Vibrator vibrator;
    private boolean initiate=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = findViewById(R.id.imageView8);
        imageview2 = findViewById(R.id.imageView5);
        vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensormanager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);

    if (sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null)
    {
        accelerometerSensor=sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        isavailable=true;

    }else
    {
        isavailable=false;
    }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        currentXcordinate= sensorEvent.values[0];
        currentYcordinate= sensorEvent.values[1];
        currentZcordinate= sensorEvent.values[2];

        if(isnotfirst)
        {
            xDifference=Math.abs(lastXcordinate-currentXcordinate);
            yDifference=Math.abs(lastYcordinate-currentYcordinate);
            zDifference=Math.abs(lastZcordinate-currentZcordinate);

                if((xDifference>threshold && yDifference>threshold)||
                        (yDifference>threshold && zDifference>threshold)||
                            (xDifference>threshold && zDifference>threshold)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));


                    } else {
                        vibrator.vibrate(500);
                    }

                    if (initiate) {
                        imageview.setImageResource(R.drawable.maroon2);
                        imageview2.setImageResource(R.drawable.sky);
                        initiate = false;
                    } else {
                        imageview.setImageResource(R.drawable.sky);
                        imageview2.setImageResource(R.drawable.maroon2);
                        initiate = true;

                    }
                }


        }


        lastXcordinate=currentXcordinate;
        lastYcordinate=currentYcordinate;
        lastZcordinate=currentZcordinate;

        isnotfirst=true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {



    }



    @Override
    protected void onResume(){

        super.onResume();
        if(isavailable)
            sensormanager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void onPause(){
        super.onPause();

        if(isavailable)
            sensormanager.unregisterListener(this);
    }








    }
