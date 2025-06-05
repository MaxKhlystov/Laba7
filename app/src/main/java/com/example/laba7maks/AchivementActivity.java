package com.example.laba7maks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.laba7maks.sharedPreferenced.PreferencesManager;

public class AchivementActivity extends BaseActivity {
    private Button btnBack;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement);

        preferencesManager = new PreferencesManager(this);
        btnBack = findViewById(R.id.btnBack);
        setupAchievements();
        btnBack.setOnClickListener(v -> goBack());
    }

    private void setupAchievements() {
        LinearLayout achievementsContainer = findViewById(R.id.achievementsContainer);

        // Ачивка 1: Угадать с первой попытки
        addAchievementCard(
                achievementsContainer,
                R.drawable.ic_achievement_1, // Замените на свою иконку
                "Мастер интуиции",
                "Угадайте число с первой попытки",
                preferencesManager.isFirstTryAchievementUnlocked()
        );

        // Добавьте другие ачивки аналогично
    }

    private void addAchievementCard(LinearLayout container, int iconRes,
                                    String title, String description, boolean isUnlocked) {
        View achievementCard = getLayoutInflater().inflate(R.layout.achivement_card, container, false);

        ImageView icon = achievementCard.findViewById(R.id.achievementIcon);
        TextView titleView = achievementCard.findViewById(R.id.achievementTitle);
        TextView descView = achievementCard.findViewById(R.id.achievementDesc);
        ImageView lockIcon = achievementCard.findViewById(R.id.lockIcon);

        icon.setImageResource(iconRes);
        titleView.setText(title);
        descView.setText(description);

        if (isUnlocked) {
            achievementCard.setAlpha(1f);
            lockIcon.setVisibility(View.GONE);
        } else {
            achievementCard.setAlpha(0.5f);
            lockIcon.setVisibility(View.VISIBLE);
        }

        container.addView(achievementCard);
    }
}