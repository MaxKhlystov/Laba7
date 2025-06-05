package com.example.laba7maks;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laba7maks.sharedPreferenced.PreferencesManager;

public class ClassicModeActivity extends BaseActivity {
    private GameLogic game;
    private TextView tvHint;
    private EditText etGuess;
    private Spinner spinnerAttempts;
    private Button btnStart, btnSubmit, btnReset, btnBack;
    private boolean guessCompNumFrom1Try = false;
    private PreferencesManager preferencesManager;
    int maxAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_mode);
        preferencesManager = new PreferencesManager(this);
        initializeСomponents();

        btnStart.setOnClickListener(v -> {
            setListenerBtnStart();
        });

        btnSubmit.setOnClickListener(v -> {
            setListenerBtnSubmit();
        });

        btnBack.setOnClickListener(v -> {
            goBack();
        });

        btnReset.setOnClickListener(v -> {
            setListenerBtnReset();
        });
    }

    private void initializeСomponents(){
        game = new GameLogic();
        tvHint = findViewById(R.id.tvHint);
        etGuess = findViewById(R.id.etGuess);
        etGuess.setVisibility(View.GONE);
        spinnerAttempts = findViewById(R.id.spinnerAttempts);
        btnStart = findViewById(R.id.btnStart);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setVisibility(View.GONE);
        btnBack = findViewById(R.id.btnBackToLevel);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAttempts.setAdapter(adapter);
        spinnerAttempts.setSelection(14);

        tvHint.setText("Выберите количество попыток и нажмите 'Старт'");
    }
    private void setListenerBtnStart(){
        maxAttempts = (int) spinnerAttempts.getSelectedItem();
        game.setMaxAttempts(maxAttempts);
        game.resetGameClassic();
        tvHint.setText("Угадайте число от 1 до 100");
        updateAttemptsDisplay();

        btnStart.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.VISIBLE);
        etGuess.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.VISIBLE);
        spinnerAttempts.setEnabled(false);
    }
    private void setListenerBtnSubmit(){
        try {
            int guess = Integer.parseInt(etGuess.getText().toString());
            if (guess < 1 || guess > 100) {
                tvHint.setText("Число должно быть от 1 до 100");
                return;
            }

            String result = game.checkGuess(guess);
            tvHint.setText(result);
            etGuess.setText("");

            if (game.isGameOver()) {
                if (checkGuessCompNumFrom1Try())
                {
                    tvHint.setText(result + "\nВы отгадали число с первой попытки!");
                    preferencesManager.unlockFirstTryAchievement();
                }
                btnSubmit.setVisibility(View.GONE);
                hideKeyboard();
                etGuess.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                spinnerAttempts.setEnabled(true);
            } else {
                updateAttemptsDisplay();
            }
        } catch (NumberFormatException e) {
            tvHint.setText("Введите число от 1 до 100");
        }
    }
    private void setListenerBtnReset(){
        game.resetGameClassic();
        tvHint.setText("Выберите количество попыток и нажмите 'Старт'");
        btnStart.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        hideKeyboard();
        etGuess.setVisibility(View.GONE);
        spinnerAttempts.setEnabled(true);
        etGuess.setText("");
    }

    private void updateAttemptsDisplay() {
        if (!game.isGameOver()) {
            tvHint.append("\nОсталось попыток: " + game.getRemainingAttempts());
        }
    }
    private boolean checkGuessCompNumFrom1Try(){
        if (guessCompNumFrom1Try){
            return false;
        }
        guessCompNumFrom1Try = true;
        if (maxAttempts - game.getRemainingAttempts() == 1){
            return true;
        }
        else return false;
    }
}