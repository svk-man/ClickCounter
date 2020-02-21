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

    private int mClickCount = 0;
    private static final String CLICK_COUNT = "clickCount";

    private TextView mTextViewClickCount;
    private ImageButton mButtonIncreaseClickCount;
    private FrameLayout mFrameIncreaseClickCount;

    private final static int ANIMATION_DELAY = 1000;
    private AnimationDrawable mAnimationStars;
    private Animation mAnimationScale;

    private SharedPreferences mSharedPreferences;
    private static final String APP_PREFERENCES = "ClickCountSettings";
    private static final String APP_PREFERENCES_CLICK_COUNT = "ClickCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewClickCount = (TextView) findViewById(R.id.text_click_count);
        mButtonIncreaseClickCount = (ImageButton) findViewById(R.id.button_increase_click_count);
        mFrameIncreaseClickCount = (FrameLayout) findViewById(R.id.frame_increase_click_count);

        mButtonIncreaseClickCount.setBackground(null);

        mAnimationScale = AnimationUtils.loadAnimation(this, R.anim.scale);

        mButtonIncreaseClickCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickIncreaseClickCount();
            }
        });

        mSharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        getClickCount();
        if (mClickCount != 0) {
            mTextViewClickCount.setText(Integer.toString(mClickCount));
        }
    }

    private void OnClickIncreaseClickCount() {
        mClickCount += 1;
        mTextViewClickCount.setText(Integer.toString(mClickCount));
        saveClickCount();

        mFrameIncreaseClickCount.setBackgroundResource(R.drawable.stars);
        mAnimationStars = (AnimationDrawable) mFrameIncreaseClickCount.getBackground();
        mButtonIncreaseClickCount.setEnabled(false);
        mAnimationStars.start();
        mButtonIncreaseClickCount.setAnimation(mAnimationScale);
        mButtonIncreaseClickCount.startAnimation(mAnimationScale);
        mButtonIncreaseClickCount.postDelayed(new Runnable() {
            @Override
            public void run() {
                mButtonIncreaseClickCount.setEnabled(true);
                mFrameIncreaseClickCount.setBackground(null);
                mAnimationStars.stop();
            }
        }, ANIMATION_DELAY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CLICK_COUNT, Integer.toString(mClickCount));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        String clickCountStr = savedInstanceState.getString(CLICK_COUNT);
        mClickCount = Integer.parseInt(clickCountStr);
        mTextViewClickCount.setText(clickCountStr);
        super.onRestoreInstanceState(savedInstanceState);
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
                intent.putExtra(CLICK_COUNT, Integer.toString(mClickCount));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getClickCount() {
        if(mSharedPreferences.contains(APP_PREFERENCES_CLICK_COUNT)) {
            mClickCount = mSharedPreferences.getInt(APP_PREFERENCES_CLICK_COUNT, 0);
        }
    }

    private void saveClickCount() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(APP_PREFERENCES_CLICK_COUNT, mClickCount);
        editor.apply();
    }
}
