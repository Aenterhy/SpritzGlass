package com.malice.spritzglass.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.glass.media.Sounds;
import com.malice.spritzglass.R;
import com.malice.spritzglass.core.BaseScrollActivity;
import com.malice.spritzglass.ui.settings.adapter.SettingsAdapter;
import com.malice.spritzglass.ui.settings.cards.SpeedActivity;
import com.malice.spritzglass.ui.settings.model.SettingType;
import com.malice.spritzglass.ui.settings.model.SettingsCard;
import com.malice.spritzglass.utils.ConstsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aenterhy.
 */
public class SettingsActivity extends BaseScrollActivity {
    private final int REQ_SPEED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<SettingsCard> settings = new ArrayList<SettingsCard>();
        settings.add(new SettingsCard(SettingType.TYPE_SPEED, "Speed", R.drawable.ic_speed, "100"));
        vCardScroll.setAdapter(new SettingsAdapter(settings));
        vCardScroll.activate();
        vCardScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingsCard card = (SettingsCard) vCardScroll.getAdapter().getItem(vCardScroll.getSelectedItemPosition());
                audio.playSoundEffect(Sounds.SELECTED);
                switch (card.getType()) {
                    case TYPE_SPEED:
//                        startActivityForResult(new Intent(SettingsActivity.this, SpeedActivity.class), REQ_SPEED);
                        Intent intent = new Intent(SettingsActivity.this, SpeedActivity.class);
                        startActivityForResult(intent.setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                        ), REQ_SPEED);

                        break;
                }
            }
        });
        hideProgress();
    }

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
    public int getProgressIcon() {
        return R.drawable.ic_gear_medium;
    }

    @Override
    public int getProgressTitle() {
        return R.string.loading;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SettingsCard card = (SettingsCard) vCardScroll.getAdapter().getItem(vCardScroll.getSelectedItemPosition());
        switch (card.getType()) {

            case TYPE_SPEED:
                card.setCurrent(String.valueOf(prefs.getInt(ConstsUtils.PREF_SPEED, 400)));
                break;
        }
        vCardScroll.getAdapter().notifyDataSetChanged();
    }
}
