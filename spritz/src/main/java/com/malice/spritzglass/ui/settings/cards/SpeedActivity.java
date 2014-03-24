package com.malice.spritzglass.ui.settings.cards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.malice.spritzglass.R;
import com.malice.spritzglass.utils.ConstsUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * @author Aenterhy.
 */
public class SpeedActivity extends Activity {

    private SharedPreferences settings;
    private final String TAG = SpeedActivity.class.getSimpleName();
    protected AudioManager audio;

    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    @InjectView(R.id.valueText)
    TextView valueText;

    @InjectView(R.id.messageTitle)
    TextView messageTitle;

    @InjectView(R.id.settingIcon)
    ImageView settingIcon;

    private GestureDetector mGestureDetector;

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        ButterKnife.inject(this);
        mGestureDetector = createGestureDetector(this);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        settings = getSharedPreferences(ConstsUtils.PREF_APP_KEY, 0);
        Log.v(TAG, "Settings restored: " + settings.getInt(ConstsUtils.PREF_SPEED, 400));
        seekBar.setMax(600);
        seekBar.setProgress(settings.getInt(ConstsUtils.PREF_SPEED, 400));
        settingIcon.setImageResource(R.drawable.ic_speed);
        messageTitle.setText(getString(R.string.speed));

        valueText.setText(Integer.toString(seekBar.getProgress()) + " wpm");
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    audio.playSoundEffect(Sounds.SUCCESS);
                    SharedPreferences.Editor editor = settings.edit();
                    Log.v(TAG, "Speed value: " + seekBar.getProgress());
                    editor.putInt(ConstsUtils.PREF_SPEED, seekBar.getProgress());
                    editor.commit();
                    finish();
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    audio.playSoundEffect(Sounds.SELECTED);
                    seekBar.incrementProgressBy(100);
                    valueText.setText(Integer.toString(seekBar.getProgress()) + " wpm");
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    seekBar.incrementProgressBy(-100);
                    valueText.setText(Integer.toString(seekBar.getProgress()) + " wpm");
                    audio.playSoundEffect(Sounds.SELECTED);
                    return true;
                }
                return false;
            }
        });
        return gestureDetector;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
}
