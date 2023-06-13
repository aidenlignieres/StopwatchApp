package com.example.stopwatchapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StopwatchActivity extends AppCompatActivity {

    private Stopwatch stopwatch;
    private Handler handler;
    private boolean isRunning;
    private TextView display;
    private Button toggle;

    private int speed;

    // Create an ActivityResultLauncher to handle the result from SettingsActivity
    private final ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            speed = data.getIntExtra("speed", 1000);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isRunning = false;
        if (savedInstanceState == null) {
            stopwatch = new Stopwatch();
            speed = 1000; // default speed
        } else {
            stopwatch = new Stopwatch(savedInstanceState.getString("value"));
            speed = savedInstanceState.getInt("speed", 1000);
            boolean running = savedInstanceState.getBoolean("running");
            if (running) {
                enableStopwatch();
            }
        }

        display = findViewById(R.id.display);
        toggle = findViewById(R.id.toggle);

        updateToggle();
        display.setText(stopwatch.toString());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("value", stopwatch.toString());
        outState.putBoolean("running", isRunning);
        outState.putInt("speed", speed);
    }

    public void toggleClicked(View view) {
        if (isRunning) {
            disableStopwatch();
        } else {
            enableStopwatch();
        }

        updateToggle();
    }

    public void settingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        settingsLauncher.launch(intent);
    }

    private void enableStopwatch() {
        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    stopwatch.tick();
                    display.setText(stopwatch.toString());

                    handler.postDelayed(this, speed);
                }
            }
        });
    }

    private void disableStopwatch() {
        isRunning = false;
    }

    private void updateToggle() {
        Resources resources = getResources();
        if (!isRunning) {
            toggle.setText(resources.getString(R.string.start_label));
        } else {
            toggle.setText(resources.getString(R.string.stop_label));
        }
    }
}
