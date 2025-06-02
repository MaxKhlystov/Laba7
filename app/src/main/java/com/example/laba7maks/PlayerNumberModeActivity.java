package com.example.laba7maks;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerNumberModeActivity extends BaseActivity {
    private int playerNumber;
    private GameLogic game = new GameLogic();
    private int attempts;
    private boolean numberSet;
    private TextView tvStatus;
    private TextView tvNumComp;
    private EditText etInput;
    private Button btnHigher;
    private Button btnLower;
    private Button btnStart;
    private Button btnAction;
    private Button btnBack;
    private Button btnReset;
    private boolean high;
    private int compNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_number);
        initializeComponents();

        btnAction.setOnClickListener(v -> {
            setListenerBtnAction();
        });

        btnStart.setOnClickListener(v -> {
            setListenerBtnStart();
        });

        btnHigher.setOnClickListener(v -> {
            high = true;
            setListenerBtnLowerAndHigner();
        });

        btnLower.setOnClickListener(v -> {
            high = false;
            setListenerBtnLowerAndHigner();
        });

        btnBack.setOnClickListener(v -> {
            goBack();
        });

        btnReset.setOnClickListener(v -> {
            setListenerBtnReset();
        });
    }
    private void initializeComponents(){
        tvStatus = findViewById(R.id.tvStatus);
        tvNumComp = findViewById(R.id.tvNumComp);
        tvNumComp.setVisibility(View.GONE);
        etInput = findViewById(R.id.etInput);
        etInput.setVisibility(View.VISIBLE);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setVisibility(View.GONE);
        btnHigher = findViewById(R.id.btnHigher);
        btnLower = findViewById(R.id.btnLower);
        btnAction = findViewById(R.id.btnAction);
        btnBack = findViewById(R.id.btnBackToLevel);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setVisibility(View.GONE);
        btnHigher.setVisibility(View.GONE);
        btnLower.setVisibility(View.GONE);
    }

    private void setListenerBtnAction(){
        if (!numberSet) {
            try {
                String input = etInput.getText().toString();
                if (input.isEmpty()) {
                    tvStatus.setText("Введите число!");
                    return;
                }
                playerNumber = Integer.parseInt(input);
                if (playerNumber < 1 || playerNumber > 100) {
                    tvStatus.setText("Число должно быть от 1 до 100");
                    return;
                }
                numberSet = true;
                game.setSecretNumber(playerNumber);
                tvStatus.setText("Компьютер угадывает число... \nВаше число: " + playerNumber);
                tvNumComp.setText("Кликайте кнопку 'Начать' и победите компьютер!");

                btnStart.setVisibility(View.VISIBLE);
                etInput.setEnabled(false);
                tvNumComp.setVisibility(View.VISIBLE);
                btnAction.setVisibility(View.GONE);
                btnHigher.setVisibility(View.GONE);
                btnLower.setVisibility(View.GONE);
                hideKeyboard();
            } catch (NumberFormatException e) {
                tvStatus.setText("Введите корректное число!");
            }
        }
    }

    private void setListenerBtnStart() {
        compNumber = (int) (Math.random() * 100) + 1;
        tvNumComp.setText("Компьютер предлагает число: " + String.valueOf(compNumber));
        btnStart.setVisibility(View.GONE);
        etInput.setVisibility(View.GONE);
        btnHigher.setVisibility(View.VISIBLE);
        btnLower.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.VISIBLE);
    }
    private void setListenerBtnReset(){
        numberSet = false;
        attempts = 0;
        game.resetGamePlayerNum();
        tvStatus.setText("Загадайте число от 1 до 100");
        etInput.setHint("Ваше число");
        etInput.setText("");
        tvNumComp.setText("Кликайте кнопку 'Начать' и победите компьютер!");
        btnAction.setVisibility(View.VISIBLE);
        etInput.setVisibility(View.VISIBLE);
        tvNumComp.setVisibility(View.GONE);
        btnStart.setVisibility(View.GONE);
        btnHigher.setVisibility(View.GONE);
        btnLower.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
    }
    private void setListenerBtnLowerAndHigner(){
        if ((high && compNumber >= playerNumber) || (!high && compNumber <= playerNumber)) {
            tvStatus.setText("Вы обманываете! Игра окончена.");
            btnHigher.setVisibility(View.GONE);
            btnLower.setVisibility(View.GONE);
            btnReset.setVisibility(View.VISIBLE);
            return;
        }
        compNumber = game.checkNumPlayer(compNumber, high);
        tvNumComp.setText("Компьютер предлагает число: " + String.valueOf(compNumber));
        if (compNumber == playerNumber) {
            tvStatus.setText("Компьютер угадал ваше число!");
            tvNumComp.setText("Ваше число: " + String.valueOf(playerNumber));
            btnHigher.setVisibility(View.GONE);
            btnLower.setVisibility(View.GONE);
        } else if (game.isGameOver()) {
            tvStatus.setText("Компьютер не угадал число");
            btnHigher.setVisibility(View.GONE);
            btnLower.setVisibility(View.GONE);
        }
    }
}