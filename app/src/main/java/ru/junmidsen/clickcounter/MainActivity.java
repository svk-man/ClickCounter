package ru.junmidsen.clickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewClickCount = findViewById(R.id.text_view_click_count);
    }

    public void onclick_increase_click_count(View view) {
        int count = Integer.parseInt(textViewClickCount.getText().toString());
        count += 1;
        textViewClickCount.setText(Integer.toString(count));
    }
}
