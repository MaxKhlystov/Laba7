package com.example.laba7maks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LevelsModeActivity extends BaseActivity {

    private Button[] levelButtons = new Button[5];
    private Button btnBackToMain, btnResetProgress;
    private SharedPreferences prefs;
    int unlockedLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_mode);

        prefs = getSharedPreferences("level_progress", MODE_PRIVATE);
        unlockedLevel = prefs.getInt("unlocked_level", 1);

        levelButtons[0] = findViewById(R.id.btnLevel1);
        levelButtons[1] = findViewById(R.id.btnLevel2);
        levelButtons[2] = findViewById(R.id.btnLevel3);
        levelButtons[3] = findViewById(R.id.btnLevel4);
        levelButtons[4] = findViewById(R.id.btnLevel5);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnResetProgress = findViewById(R.id.btnResetProgress);

        setEnableBtn();
        updateResetButtonVisibility();

        btnBackToMain.setOnClickListener(v -> finish());

        btnResetProgress.setOnClickListener(v -> {
            // Сбрасываем прогресс до 1 уровня
            prefs.edit().putInt("unlocked_level", 1).apply();
            unlockedLevel = 1;
            setEnableBtn();
            updateResetButtonVisibility();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        unlockedLevel = prefs.getInt("unlocked_level", 1);
        setEnableBtn();
        updateResetButtonVisibility();
    }

    private void startLevel(int level) {
        Intent intent = new Intent(this, GameLevelActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private void setEnableBtn() {
        for (int i = 0; i < levelButtons.length; i++) {
            int level = i + 1;
            Button btn = levelButtons[i];
            btn.setEnabled(level <= unlockedLevel);
            btn.setAlpha(level <= unlockedLevel ? 1f : 0.5f);
            btn.setOnClickListener(v -> startLevel(level));
        }
    }

    private void updateResetButtonVisibility() {
        // Показываем кнопку сброса только если все уровни пройдены
        btnResetProgress.setVisibility(unlockedLevel >= 5 ? View.VISIBLE : View.GONE);
    }
}