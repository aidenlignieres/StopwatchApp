package com.example.stopwatchapp;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Stopwatch {
    private int hours, minutes, seconds;

    Stopwatch() {
        hours = minutes = seconds = 0;
    }

    Stopwatch(String value) {
        String[] tokens = value.split(":");
        hours = Integer.parseInt(tokens[0]);
        minutes = Integer.parseInt(tokens[1]);
        seconds = Integer.parseInt(tokens[2]);
    }

    void tick() {
        ++seconds;
        if (seconds == 60) {
            seconds = 0;
            ++minutes;
            if (minutes == 60) {
                minutes = 0;
                ++hours;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes,seconds);
    }
}
