package com.example.edu.pedometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    SeekBar seekBar;
    TextView textViewThreshold;
    TextView textViewSteps;
    TextView textViewGx, textViewGy, textViewGz;
    Button buttonReset;
    int threshold;
    float previousX, currentX;
    float previousY, currentY;
    float previousZ, currentZ;
    float steps;
    float acceleration;

    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textViewThreshold= findViewById(R.id.textViewThreshold);
        textViewSteps = findViewById(R.id.textViewSteps);
        textViewGx = findViewById(R.id.textViewGx);
        textViewGy = findViewById(R.id.textViewGy);
        textViewGz = findViewById(R.id.textViewGz);
        buttonReset = findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(this);

//        threshold = seekBar.getProgress();
        threshold = 3;
        seekBar.setProgress(threshold);
        previousX = currentX = steps = 0;
        previousY = currentY = 0;
        previousZ = currentZ =0;
        acceleration = 0.0f;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                                        SensorManager.SENSOR_DELAY_NORMAL);
        textViewThreshold.setText("Threshold: " + String.valueOf(threshold));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                threshold = seekBar.getProgress();
                textViewThreshold.setText("Threshold: " + String.valueOf(threshold));
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        currentX = x;
        currentY = y;
        currentZ = z;
        if(Math.abs(currentY - previousY) > threshold){
            steps++;
            textViewSteps.setText("Step: " + String.valueOf(steps));
        }
        textViewGx.setText("x: " + String.valueOf(x));
        textViewGy.setText("y: " + String.valueOf(y));
        textViewGz.setText("z: " + String.valueOf(z));
        previousX = x;
        previousY = y;
        previousZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        previousX = currentX = steps = 0;
        previousY = currentY = 0;
        previousZ = currentZ =0;

        textViewSteps.setText("Step: " + String.valueOf(steps));
    }
}
