package com.example.zdroa.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_t);

    }

    public void test(View v) {
        TextView textView = (TextView) findViewById(R.id.test_TV);

        textView.setText(" w e w ".trim());
        textView.append(" w e w ");
    }
}
