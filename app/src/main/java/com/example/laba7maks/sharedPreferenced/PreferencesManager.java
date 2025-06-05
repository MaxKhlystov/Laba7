package com.example.laba7maks.sharedPreferenced;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREFS_NAME = "level_progress";
    private static final String KEY_UNLOCKED_LEVEL = "unlocked_level";

    private final SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Сохраняет последний открытый уровень
    public void saveUnlockedLevel(int level) {
        sharedPreferences.edit()
                .putInt(KEY_UNLOCKED_LEVEL, level)
                .apply();
    }

    // Возвращает последний открытый уровень (по умолчанию 1)
    public int getUnlockedLevel() {
        return sharedPreferences.getInt(KEY_UNLOCKED_LEVEL, 1);
    }

    // Сбрасывает прогресс до 1 уровня
    public void resetProgress() {
        saveUnlockedLevel(1);
    }
}