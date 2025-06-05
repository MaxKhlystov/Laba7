package com.example.laba7maks.sharedPreferenced;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREFS_NAME = "game_prefs";
    private static final String KEY_UNLOCKED_LEVEL = "unlocked_level";
    private static final String KEY_ACHIEVEMENT_1 = "achievement_first_try";
    private static final String KEY_ACHIEVEMENT_2 = "achievement_15_lose";
    private static final String KEY_ACHIEVEMENT_3 = "achievement_15_win";

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

    public void unlockAchievement(String achievementKey) {
        sharedPreferences.edit().putBoolean(achievementKey, true).apply();
    }
    public boolean isAchievementUnlocked(String achievementKey) {
        return sharedPreferences.getBoolean(achievementKey, false);
    }
    //Первая ачивка
    public void unlockFirstAchievement() {
        unlockAchievement(KEY_ACHIEVEMENT_1);
    }
    public boolean isFirstAchievementUnlocked() {
        return isAchievementUnlocked(KEY_ACHIEVEMENT_1);
    }
    //Вторая ачивка
    public void unlockSecondAchievement() {
        unlockAchievement(KEY_ACHIEVEMENT_2);
    }
    public boolean isSecondAchievementUnlocked() {
        return isAchievementUnlocked(KEY_ACHIEVEMENT_2);
    }
    //Третья ачивка
    public void unlockThirdAchievement() {
        unlockAchievement(KEY_ACHIEVEMENT_3);
    }
    public boolean isThirdAchievementUnlocked() {
        return isAchievementUnlocked(KEY_ACHIEVEMENT_3);
    }

    //Сбросить ачивки
    public void resetAchivement(){
        sharedPreferences.edit().putBoolean(KEY_ACHIEVEMENT_1, false).apply();
        sharedPreferences.edit().putBoolean(KEY_ACHIEVEMENT_2, false).apply();
        sharedPreferences.edit().putBoolean(KEY_ACHIEVEMENT_3, false).apply();
    }
}