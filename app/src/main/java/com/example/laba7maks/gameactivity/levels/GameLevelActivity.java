package com.example.laba7maks.gameactivity.levels;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.laba7maks.BaseActivity;
import com.example.laba7maks.R;
import com.example.laba7maks.sharedPreferenced.PreferencesManager;
import java.util.Random;

public class GameLevelActivity extends BaseActivity {
    private int level, maxNumber, maxAttempts;
    private int targetNumber, attemptsLeft;
    private TextView tvStatus;
    private EditText etInput;
    private Button btnSubmit, btnAgain, btnBackToLevel;
    private boolean isGameOver = false;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);

        preferencesManager = new PreferencesManager(this);
        level = getIntent().getIntExtra("level", 1);
        initializeComponents();
        setupLevel(level);
        generateNumber();
        btnSubmit.setOnClickListener(v -> checkGuess());

        btnBackToLevel.setOnClickListener(v -> goBack());
        btnAgain.setOnClickListener(v -> setActionBtnAgain());
    }

    private void initializeComponents() {
        tvStatus = findViewById(R.id.tvStatus);
        etInput = findViewById(R.id.etInput);
        etInput.setEnabled(true);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnAgain = findViewById(R.id.btnAgain);
        btnBackToLevel = findViewById(R.id.btnBackToLevel);
    }

    private void setActionBtnAgain() {
        generateNumber();
        attemptsLeft = maxAttempts;
        btnSubmit.setEnabled(true);
        etInput.setText("");
        tvStatus.setText("Уровень " + level + ": угадайте число от 1 до " + maxNumber + "\nУ вас есть " + maxAttempts + " попыток");
        btnSubmit.setVisibility(View.VISIBLE);
        btnAgain.setVisibility(View.GONE);
        btnBackToLevel.setVisibility(View.VISIBLE);
        etInput.setEnabled(true);
        isGameOver = false;
    }

    private void setupLevel(int level) {
        switch (level) {
            case 1: maxNumber = 20; maxAttempts = 6; break;
            case 2: maxNumber = 40; maxAttempts = 8; break;
            case 3: maxNumber = 60; maxAttempts = 10; break;
            case 4: maxNumber = 80; maxAttempts = 12; break;
            case 5: maxNumber = 100; maxAttempts = 8; break;
            default: maxNumber = 100; maxAttempts = 10;
        }
        attemptsLeft = maxAttempts;
        tvStatus.setText("Уровень " + level + ": угадайте число от 1 до " + maxNumber + "\nУ вас есть " + maxAttempts + " попыток");
    }

    private void generateNumber() {
        targetNumber = new Random().nextInt(maxNumber) + 1;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(etInput.getText().toString());

            if (guess < 1 || guess > maxNumber) {
                tvStatus.setText("Введите число от 1 до " + maxNumber);
                return;
            }

            attemptsLeft--;

            if (guess == targetNumber) {
                tvStatus.setText("Поздравляем! Вы прошли уровень " + level + "\nЗагаданное число: " + targetNumber);
                btnSubmit.setVisibility(View.GONE);
                btnAgain.setVisibility(View.GONE);
                isGameOver = true;
                etInput.setEnabled(false);
                hideKeyboard();
                etInput.setText("");

                int unlockedLevel = preferencesManager.getUnlockedLevel();
                if (level == unlockedLevel && level < 5) {
                    preferencesManager.saveUnlockedLevel(level + 1);
                }

            } else if (attemptsLeft > 0) {
                tvStatus.setText((guess < targetNumber ? "Загаданное число больше" : "Загаданное число меньше")
                        + "\nОсталось попыток: " + attemptsLeft);
                etInput.setText("");
            } else {
                tvStatus.setText("Вы проиграли! Загаданное число было: " + targetNumber);
                btnSubmit.setVisibility(View.GONE);
                btnAgain.setVisibility(View.VISIBLE);
                isGameOver = true;
                etInput.setEnabled(false);
                hideKeyboard();
                etInput.setText("");
            }

        } catch (NumberFormatException e) {
            tvStatus.setText("Введите допустимое число");
        }
    }
}