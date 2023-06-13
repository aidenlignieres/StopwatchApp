package com.example.stopwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    public static final int SETTINGS_REQUEST = 1234;
    private EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userInput = findViewById(R.id.userInput);
    }

    public void doneClicked(View view) {
        String text = userInput.getText().toString();
        int speed = Integer.parseInt(text);

        Intent intent = new Intent();
        intent.putExtra("speed", speed);
        setResult(RESULT_OK, intent);
        finish();
    }
}