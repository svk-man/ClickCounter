package ru.junmidsen.clickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView clickCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        clickCountTextView = (TextView) findViewById(R.id.text_click_count);

        Intent intent = getIntent();
        if (intent.hasExtra("clickCount")) {
            clickCountTextView.setText(intent.getStringExtra("clickCount"));
        }
    }
}
