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

    public void saveUnlockedLevel(int level) {
        sharedPreferences.edit()
                .putInt(KEY_UNLOCKED_LEVEL, level)
                .apply();
    }

    public int getUnlockedLevel() {
        return sharedPreferences.getInt(KEY_UNLOCKED_LEVEL, 1);
    }

    public void resetProgress() {
        saveUnlockedLevel(1);
    }
}