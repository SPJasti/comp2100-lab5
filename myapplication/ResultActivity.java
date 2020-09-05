package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultsText = findViewById(R.id.resultsText);
        int choice1 = getIntent().getIntExtra("choice1", 0);
        resultsText.setText("Japanese - " + choice1);

        TextView resultsText2 = findViewById(R.id.resultsText2);
        int choice2 = getIntent().getIntExtra("choice2", 0);
        resultsText2.setText("Indian - " + choice2);

        TextView resultsText3 = findViewById(R.id.resultsText3);
        int choice3 = getIntent().getIntExtra("choice3", 0);
        resultsText3.setText("French - " + choice3);
    }

    public void choiceBack(View view){
        finish();
    }
}
