package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TextView showValue;
    int counter,counter2,counter3=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showValue = findViewById(R.id.button);
    }

    public void choice1Pressed(View view){
        //TextView textView = findViewById(R.id.text);
        //textView.setText("Japanese it is !");
        Button button = findViewById(R.id.button);
        counter++;
        button.setText("Japanese ("+Integer.toString(counter)+")");
        //showValue.setText(counter);
    }

    public void choice2Pressed(View view){
        //TextView textView = findViewById(R.id.text);
        //textView.setText("Indian it is !");
        Button button = findViewById(R.id.button2);
        counter2++;
        button.setText("Indian ("+Integer.toString(counter2)+")");
    }

    public void choice3Pressed(View view){
        //TextView textView = findViewById(R.id.text);
        //textView.setText("French it is !");
        Button button = findViewById(R.id.button3);
        counter3++;
        button.setText("French ("+Integer.toString(counter3)+")");
    }

    public void NonePressed(View view){
        //TextView textView = findViewById(R.id.text);
        //textView.setText("None it is !");
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void Result(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("choice1", counter);
        intent.putExtra("choice2", counter2);
        intent.putExtra("choice3", counter3);
        startActivity(intent);
    }
}
