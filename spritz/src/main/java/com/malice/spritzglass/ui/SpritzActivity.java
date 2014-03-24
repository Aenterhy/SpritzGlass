package com.malice.spritzglass.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import com.andrewgiang.textspritzer.lib.SpritzerTextView;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.malice.spritzglass.R;
import com.malice.spritzglass.utils.ConstsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aenterhy.
 */
public class SpritzActivity extends Activity {
    public static final String TAG_TEXT = "TEXT";


    @InjectView(R.id.spritzerTextView)
    SpritzerTextView spritzerTextView;

    @InjectExtra(TAG_TEXT)
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spritz);
        ButterKnife.inject(this);
        Dart.inject(this);
        SharedPreferences settings = getSharedPreferences(ConstsUtils.PREF_APP_KEY, 0);

        spritzerTextView.setTextScaleX(1.f);
        spritzerTextView.setTextSize(30);
        spritzerTextView.setTypeface(Typeface.MONOSPACE);
        spritzerTextView.setSpritzText(text);
        spritzerTextView.setWpm(settings.getInt(ConstsUtils.PREF_SPEED, 400));
        spritzerTextView.play();
    }

}
