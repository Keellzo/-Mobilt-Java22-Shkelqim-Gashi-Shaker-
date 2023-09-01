package com.example.shaker;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.graphics.Color;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.shaker.fragments.FragmentResults;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private ImageView imageView;
    private FragmentResults fragmentResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (fragmentResults == null) {
            fragmentResults = new FragmentResults();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.results, fragmentResults).commit();
        }

        imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Animate if shaking
        float deltaX = Math.abs(lastX - x);
        float deltaY = Math.abs(lastY - y);
        float deltaZ = Math.abs(lastZ - z);

        if (deltaX > 8 || deltaY > 8 || deltaZ > 8) {
            ImageButton imageButton = findViewById(R.id.imageButton);
            imageButton.setBackgroundColor(Color.rgb(255, 165, 0));
            Log.d("shaking", "move move shake shake!");

            // Load shake animation
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            imageButton.startAnimation(shake);

            // Reset ImageButton to default after 3 seconds
            new android.os.Handler().postDelayed(
                    () -> {
                        // Remove animation
                        imageButton.clearAnimation();

                        // Reset background color to default (or whatever you want)
                        imageButton.setBackgroundColor(Color.LTGRAY);
                    },
                    3000  // Delay in milliseconds
            );
        }


        // Rotate image
        imageView.setRotation(x * 10);

        // Update UI components
        fragmentResults.setXYZ(x, y, z);

        lastX = x;
        lastY = y;
        lastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
