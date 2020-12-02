package com.example.betonit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BettorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bettor);

        queryBets();
    }

    private void queryBets() {
    }
}