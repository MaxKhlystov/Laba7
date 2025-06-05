package com.example.laba7maks;

import android.os.Bundle;
import android.widget.Button;


public class AchivementActivity extends BaseActivity{
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        this.setContentView(R.layout.activity_achivement);
        btnBack.setOnClickListener(v -> goBack());
    }
}
