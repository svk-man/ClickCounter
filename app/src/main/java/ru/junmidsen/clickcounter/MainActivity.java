package ru.junmidsen.clickcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CLICK_COUNT = "clickCount";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.click_count:
                Intent intent = new Intent(this, DetailActivity.class);
                String countStr = textViewClickCount.getText().toString();
                intent.putExtra("clickCount", countStr);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CLICK_COUNT, textViewClickCount.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        textViewClickCount.setText(savedInstanceState.getString(CLICK_COUNT));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
