package com.malice.spritzglass.core;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.analytics.tracking.android.EasyTracker;
import com.malice.spritzglass.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Malice.
 *         Messages handling activity
 */
public class MessageActivity extends Activity {
    private final String TAG = MessageActivity.class.getSimpleName();

    @InjectExtra("messageTitle")
    int eMessageTitle;
    @InjectExtra("messageText")
    int eMessageText;
    @InjectExtra("messageIcon")
    int eMessageIcon;

    @InjectView(R.id.messageTitle)
    TextView messageTitle;

    @InjectView(R.id.messageText)
    TextView messageText;

    @InjectView(R.id.settingIcon)
    ImageView messageIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Dart.inject(this);
        ButterKnife.inject(this);

        if (eMessageTitle != 0)
            messageTitle.setText(getString(eMessageTitle));
        else
            messageTitle.setVisibility(View.GONE);

        if (eMessageText != 0)
            messageText.setText(getString(eMessageText));
        else
            messageText.setVisibility(View.GONE);

        messageIcon.setImageDrawable(getResources().getDrawable(eMessageIcon));
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }
}
