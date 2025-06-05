package com.example.laba7maks.sharedPreferenced;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_UNLOCKED_LEVEL = "unlocked_level";
    private static final String KEY_ACHIEVEMENT_1 = "achievement_first_try";
    // Добавьте другие ключи для ачивок

    private final SharedPreferences sharedPreferences;
    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    // Методы для уровней
    public void saveUnlockedLevel(int level) {
        sharedPreferences.edit().putInt(KEY_UNLOCKED_LEVEL, level).apply();
    }
    public int getUnlockedLevel() {
        return sharedPreferences.getInt(KEY_UNLOCKED_LEVEL, 1);
    }
    public void resetProgress() {
        sharedPreferences.edit().putInt(KEY_UNLOCKED_LEVEL, 1).apply();
    }

    // Методы для ачивок
    public void unlockAchievement(String achievementKey) {
        sharedPreferences.edit().putBoolean(achievementKey, true).apply();
    }
    public boolean isAchievementUnlocked(String achievementKey) {
        return sharedPreferences.getBoolean(achievementKey, false);
    }
    // Специфичные методы для ачивок
    public void unlockFirstTryAchievement() {
        unlockAchievement(KEY_ACHIEVEMENT_1);
    }
    public boolean isFirstTryAchievementUnlocked() {
        return isAchievementUnlocked(KEY_ACHIEVEMENT_1);
    }
}