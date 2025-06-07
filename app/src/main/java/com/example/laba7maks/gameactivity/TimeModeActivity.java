package com.example.laba7maks.gameactivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laba7maks.BaseActivity;
import com.example.laba7maks.R;

public class TimeModeActivity extends BaseActivity {
    private GameLogic game;
    private TextView tvHint, tvTimer;
    private EditText etGuess;
    private Spinner spinnerTime;
    private Button btnStart, btnSubmit, btnReset, btnBack;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_mode);

        initializeComponents();
        setupTimerSpinner();

        btnStart.setOnClickListener(v -> setListenerBtnStart());
        btnSubmit.setOnClickListener(v -> setListenerBtnSubmit());
        btnBack.setOnClickListener(v -> goBack());
        btnReset.setOnClickListener(v -> setListenerBtnReset());
    }

    private void initializeComponents() {
        game = new GameLogic();
        tvHint = findViewById(R.id.tvHint);
        tvTimer = findViewById(R.id.tvTimer);
        etGuess = findViewById(R.id.etGuess);
        spinnerTime = findViewById(R.id.spinnerTime);
        btnStart = findViewById(R.id.btnStart);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        btnBack = findViewById(R.id.btnBack);

        tvHint.setText("Выберите время и нажмите 'Старт'");
        etGuess.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
    }

    private void setupTimerSpinner() {
        Integer[] timeOptions = {15, 30, 60, 90, 120};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, timeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);
    }

    private void setListenerBtnStart() {
        int selectedTime = (int) spinnerTime.getSelectedItem();
        game.resetTimeMode(selectedTime);

        tvHint.setText("Угадайте число от 1 до 100");
        btnStart.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.VISIBLE);
        etGuess.setVisibility(View.VISIBLE);
        etGuess.setEnabled(true);
        btnReset.setVisibility(View.VISIBLE);
        spinnerTime.setEnabled(false);

        startTimer();
    }

    private void setListenerBtnSubmit() {
        try {
            int guess = Integer.parseInt(etGuess.getText().toString());
            if (guess < 1 || guess > 100) {
                tvHint.setText("Число должно быть от 1 до 100");
                return;
            }
            if (etGuess.getText().toString().isEmpty()) {
                tvHint.setText("Введите число от 1 до 100");
                return;
            }
            String result = game.checkGuess(guess);
            tvHint.setText(result);
            etGuess.setText("");

            if (game.isGameOver() || game.isTimeUp()) {
                endGame();
            }
        } catch (NumberFormatException e) {
            tvHint.setText("Введите число от 1 до 100");
        }
    }

    private void setListenerBtnReset() {
        stopTimer();
        game.resetGameClassic(false);
        tvHint.setText("Выберите время и нажмите 'Старт'");
        tvTimer.setText("");
        btnStart.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        spinnerTime.setEnabled(true);
        hideKeyboard();
        etGuess.setVisibility(View.GONE);
        etGuess.setText("");
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long remaining = game.getRemainingTime() / 1000;
                tvTimer.setText("Осталось: " + remaining + " сек");

                if (game.isTimeUp()) {
                    tvTimer.setText("Время закончилось и вы проиграли!");
                    endGame();
                } else {
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void endGame() {
        stopTimer();
        btnSubmit.setVisibility(View.GONE);
        etGuess.setEnabled(false);
        btnStart.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.GONE);
        spinnerTime.setEnabled(true);
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}