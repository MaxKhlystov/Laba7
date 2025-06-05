package com.example.laba7maks.achivement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.example.laba7maks.R;

public class AchievementBanner {

    public static void show(
            View anchorView,
            String title,
            @Nullable String message,
            @DrawableRes int iconRes,
            int duration
    ) {
        // Создаем кастомное view
        View customView = LayoutInflater.from(anchorView.getContext())
                .inflate(R.layout.banner_achievement, null);

        // Настраиваем данные
        ((TextView) customView.findViewById(R.id.title)).setText(title);
        if (message != null) {
            ((TextView) customView.findViewById(R.id.message)).setText(message);
        }
        ((ImageView) customView.findViewById(R.id.icon)).setImageResource(iconRes);

        // Создаем Snackbar
        Snackbar snackbar = Snackbar.make(anchorView, "", duration);
        View snackbarView = snackbar.getView();

        // Настраиваем внешний вид
        snackbarView.setBackgroundColor(0x00000000); // Прозрачный фон
        ((ViewGroup) snackbarView).removeAllViews(); // Удаляем стандартный контент
        ((ViewGroup) snackbarView).addView(customView); // Добавляем наш layout

        // Анимация появления
        customView.setAlpha(0f);
        customView.setTranslationY(-100f);
        customView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(300)
                .start();

        snackbar.show();
    }

    // Упрощенный метод
    public static void showShort(View anchorView, String title) {
        show(anchorView, title, null, R.drawable.ic_trophy, Snackbar.LENGTH_SHORT);
    }
}