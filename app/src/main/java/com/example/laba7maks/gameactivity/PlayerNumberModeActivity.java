package com.example.laba7maks.gameactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laba7maks.BaseActivity;
import com.example.laba7maks.R;
import com.example.laba7maks.achivement.AchievementBanner;
import com.example.laba7maks.sharedPreferenced.PreferencesManager;

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
    private PreferencesManager preferencesManager;
    private boolean is4Achievemt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_number);
        preferencesManager = new PreferencesManager(this);
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
        setVisibleBtnLowerAndHigher(1);
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
                hideKeyboard();
            } catch (NumberFormatException e) {
                tvStatus.setText("Введите корректное число!");
            }
        }
    }

    private void setListenerBtnStart() {
        compNumber = (int) (Math.random() * 1) + 1;
        tvNumComp.setText("Компьютер предлагает число: " + String.valueOf(compNumber));
        btnStart.setVisibility(View.GONE);
        etInput.setVisibility(View.GONE);
        btnHigher.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.VISIBLE);
        setVisibleBtnLowerAndHigher(0);
        checkNumFromStart();
    }
    private void setListenerBtnReset(){
        numberSet = false;
        attempts = 0;
        game.resetGamePlayerNum();
        tvStatus.setText("Загадайте число от 1 до 100");
        etInput.setText("");
        tvNumComp.setText("Кликайте кнопку 'Начать' и победите компьютер!");
        btnAction.setVisibility(View.VISIBLE);
        etInput.setVisibility(View.VISIBLE);
        etInput.setEnabled(true);
        tvNumComp.setVisibility(View.GONE);
        btnStart.setVisibility(View.GONE);
        setVisibleBtnLowerAndHigher(1);
        btnReset.setVisibility(View.GONE);
    }
    private void setListenerBtnLowerAndHigner(){
        if ((high && compNumber >= playerNumber) || (!high && compNumber <= playerNumber)) {
            tvStatus.setText("Вы обманываете! Игра окончена.");
            tvNumComp.setText("Нажмите кнопку 'Заново' и постарайтесь не обманывать!");
            setVisibleBtnLowerAndHigher(1);
            btnReset.setVisibility(View.VISIBLE);
            return;
        }
        compNumber = game.checkNumPlayer(compNumber, high);
        tvNumComp.setText("Компьютер предлагает число: " + String.valueOf(compNumber));
        if (compNumber == playerNumber) {
            tvStatus.setText("Компьютер угадал ваше число!");
            tvNumComp.setText("Ваше число: " + String.valueOf(playerNumber));
            setVisibleBtnLowerAndHigher(1);
        } else if (game.isGameOver()) {
            tvStatus.setText("Компьютер не угадал число");
            tvNumComp.setText("Вы выйграли компьютер!");
            setVisibleBtnLowerAndHigher(1);
        }
    }
    private void setVisibleBtnLowerAndHigher(int visible){
        if (visible == 0){
            btnHigher.setVisibility(View.VISIBLE);
            btnLower.setVisibility(View.VISIBLE);
        }else {
            btnHigher.setVisibility(View.GONE);
            btnLower.setVisibility(View.GONE);
        }
    }
    private void checkNumFromStart(){
        if (compNumber == playerNumber) {
            tvStatus.setText("Компьютер угадал ваше число!");
            tvNumComp.setText("Ваше число: " + String.valueOf(playerNumber));
            setVisibleBtnLowerAndHigher(1);
            if (!is4Achievemt){
                checkAchievemt4();
                is4Achievemt = true;
            }
        }
    }
    private void checkAchievemt4(){
        AchievementBanner.showShort(
                findViewById(android.R.id.content),
                "Новое достижение!"
        );
        preferencesManager.unlockFourAchievement();
    }
}