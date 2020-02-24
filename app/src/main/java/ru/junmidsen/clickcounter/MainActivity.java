package ru.junmidsen.clickcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CLICK_COUNT = "clickCount";
    private static final String APP_PREFERENCES = "ClickCountSettings";
    private static final String APP_PREFERENCES_CLICK_COUNT = "ClickCount";
    private static final int ANIMATION_DELAY = 1000;

    private int clickCount = 0;

    private TextView clickCountTextView;
    private ImageButton increaseClickCountButton;
    private FrameLayout increaseClickCountFrame;

    private AnimationDrawable animationStars;
    private Animation animationScale;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCountTextView = (TextView) findViewById(R.id.text_click_count);
        increaseClickCountButton = (ImageButton) findViewById(R.id.button_increase_click_count);
        increaseClickCountFrame = (FrameLayout) findViewById(R.id.frame_increase_click_count);

        increaseClickCountButton.setBackground(null);

        animationScale = AnimationUtils.loadAnimation(this, R.anim.scale);

        increaseClickCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickIncreaseClickCount();
            }
        });

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getClickCount();
        if (clickCount != 0) {
            clickCountTextView.setText(String.valueOf(clickCount));
        }
    }

    private void OnClickIncreaseClickCount() {
        clickCount ++;
        clickCountTextView.setText(String.valueOf(clickCount));
        saveClickCount();

        increaseClickCountFrame.setBackgroundResource(R.drawable.stars);
        animationStars = (AnimationDrawable) increaseClickCountFrame.getBackground();
        increaseClickCountButton.setEnabled(false);
        animationStars.start();
        increaseClickCountButton.setAnimation(animationScale);
        increaseClickCountButton.startAnimation(animationScale);
        increaseClickCountButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                increaseClickCountButton.setEnabled(true);
                increaseClickCountFrame.setBackground(null);
                animationStars.stop();
            }
        }, ANIMATION_DELAY);
    }

    private void getClickCount() {
        if(sharedPreferences.contains(APP_PREFERENCES_CLICK_COUNT)) {
            clickCount = sharedPreferences.getInt(APP_PREFERENCES_CLICK_COUNT, 0);
        }
    }

    private void saveClickCount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_PREFERENCES_CLICK_COUNT, clickCount);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.click_count) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(CLICK_COUNT, Integer.toString(clickCount));
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CLICK_COUNT, Integer.toString(clickCount));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        clickCount = Integer.parseInt(savedInstanceState.getString(CLICK_COUNT));
        clickCountTextView.setText(clickCount);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
