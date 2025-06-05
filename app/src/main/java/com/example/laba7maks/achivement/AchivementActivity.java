package com.example.laba7maks.achivement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laba7maks.BaseActivity;
import com.example.laba7maks.R;
import com.example.laba7maks.sharedPreferenced.PreferencesManager;

public class AchivementActivity extends BaseActivity {
    private Button btnBack;
    private Button btnResetAchivement;
    private PreferencesManager preferencesManager;
    private View achievementCard;
    private ImageView icon;
    private TextView titleView;
    private TextView descView;
    private ImageView lockIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement);

        preferencesManager = new PreferencesManager(this);
        btnBack = findViewById(R.id.btnBack);
        btnResetAchivement = findViewById(R.id.btnResetAchivement);
        setupAchievements();
        btnBack.setOnClickListener(v -> goBack());
        btnResetAchivement.setOnClickListener(v->setListenerBtnResetAchivement());
    }

    private void setupAchievements() {
        LinearLayout achievementsContainer = findViewById(R.id.achievementsContainer);

        // Ачивка 1: Угадать с первой попытки
        addAchievementCard(
                achievementsContainer,
                R.drawable.ic_achievement_1, // Замените на свою иконку
                "Я даже не напрягался",
                "Угадайте число с первой попытки",
                preferencesManager.isFirstAchievementUnlocked()
        );

        // Ачивка 2: Не угадать с 15 попыток
        addAchievementCard(
                achievementsContainer,
                R.drawable.ic_achievement_2, // Замените на свою иконку
                "Тугодум",
                "Не угадать число с 15 попыток",
                preferencesManager.isSecondAchievementUnlocked()
        );

        // Ачивка 3: Угадать с последней попытки
        addAchievementCard(
                achievementsContainer,
                R.drawable.ic_achievement_3, // Замените на свою иконку
                "На волоске",
                "Угадать число на последней попытке",
                preferencesManager.isThirdAchievementUnlocked()
        );
    }

    private void addAchievementCard(LinearLayout container, int iconRes,
                                    String title, String description, boolean isUnlocked) {
        achievementCard = getLayoutInflater().inflate(R.layout.achivement_card, container, false);

        icon = achievementCard.findViewById(R.id.achievementIcon);
        titleView = achievementCard.findViewById(R.id.achievementTitle);
        descView = achievementCard.findViewById(R.id.achievementDesc);
        lockIcon = achievementCard.findViewById(R.id.lockIcon);

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
    private void setListenerBtnResetAchivement(){
        preferencesManager.resetAchivement();
        achievementCard.setAlpha(0.5f);
        lockIcon.setVisibility(View.VISIBLE);
    }
}