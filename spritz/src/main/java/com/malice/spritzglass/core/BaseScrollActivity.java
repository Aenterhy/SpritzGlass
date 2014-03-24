package com.malice.spritzglass.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollView;
import com.malice.spritzglass.R;
import com.malice.spritzglass.utils.ConstsUtils;
import com.malice.spritzglass.widgets.SliderView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * @author Malice.
 */
public abstract class BaseScrollActivity extends Activity {
    protected AudioManager audio;
    protected SharedPreferences prefs;

    @InjectView(R.id.card_scroll)
    protected CardScrollView vCardScroll;
    @InjectView(R.id.slider)
    SliderView slider;
    @InjectView(R.id.icon)
    ImageView icon;
    @InjectView(R.id.label)
    TextView label;
    @InjectView(R.id.progress)
    LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_scroll);
        ButterKnife.inject(this);
        prefs = getSharedPreferences(ConstsUtils.PREF_APP_KEY, 0);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        icon.setImageDrawable(getResources().getDrawable(getProgressIcon()));
        label.setText(getResources().getString(getProgressTitle()));
        slider.startIndeterminate();
    }

    protected void showMessage(int messageTitle, int messageText, int icon, boolean finish) {
        Intent messageIntent = new Intent(this, MessageActivity.class);
        messageIntent.putExtra("messageTitle", messageTitle);
        messageIntent.putExtra("messageText", messageText);
        messageIntent.putExtra("messageIcon", icon);
        startActivity(messageIntent);
        if (finish)
            finish();
    }

    protected void hideProgress() {
        slider.stopProgress();
        progress.setVisibility(View.GONE);
        vCardScroll.setVisibility(View.VISIBLE);
    }

    public abstract int getProgressIcon();

    public abstract int getProgressTitle();
}
