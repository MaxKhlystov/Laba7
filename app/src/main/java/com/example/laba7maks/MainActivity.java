package com.example.laba7maks;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import com.example.laba7maks.achivement.AchivementActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClassic = findViewById(R.id.btnClassic);
        Button btnPlayerNumber = findViewById(R.id.btnPlayerNumber);
        Button btnLevelsMode = findViewById(R.id.btnLevelsMode);
        Button btnTimeMode = findViewById(R.id.btnTimeMode);
        Button btnAchivements = findViewById(R.id.btnAchivements);
        Button btnExit = findViewById(R.id.btnExit);

        btnClassic.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, ClassicModeActivity.class));
            } catch (Exception e) {
                Log.e("MainActivity", "Ошибка запуска ClassicModeActivity", e);
            }
        });

        btnPlayerNumber.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, PlayerNumberModeActivity.class));
            } catch (Exception e) {
                Log.e("MainActivity", "Ошибка запуска PlayerNumberModeActivity", e);
            }
        });
        btnLevelsMode.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, LevelsModeActivity.class));
            } catch (Exception e) {
                Log.e("MainActivity", "Ошибка запуска LevelsModeActivity", e);
            }
        });

        btnTimeMode.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, TimeModeActivity.class));
            } catch (Exception e) {
                Log.e("MainActivity", "Ошибка запуска TimeModeActivity", e);
            }
        });

        btnAchivements.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, AchivementActivity.class));
            } catch (Exception e) {
                Log.e("MainActivity", "Ошибка запуска AchivementActivity", e);
            }
        });

        btnExit.setOnClickListener(v -> finish());
    }
}