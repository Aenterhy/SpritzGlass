package com.malice.spritzglass.ui.settings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollAdapter;
import com.malice.spritzglass.R;
import com.malice.spritzglass.SpritzGlass;
import com.malice.spritzglass.ui.settings.model.SettingsCard;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Malice.
 */
public class SettingsAdapter extends CardScrollAdapter {

    private final String TAG = SettingsAdapter.class.getSimpleName();
    private final List<SettingsCard> settings;

    public SettingsAdapter(List<SettingsCard> settings) {
        this.settings = settings;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(SpritzGlass.getInstance()).inflate(R.layout.settings_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        SettingsCard card = settings.get(i);

        holder.settingIcon.setImageDrawable(SpritzGlass.getInstance().getResources().getDrawable(card.getIcon()));
        holder.settingTitle.setText(card.getTitle());
        holder.currentValue.setText(card.getCurrent());
        return view;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int i) {
        return settings.get(i);
    }

    @Override
    public int findIdPosition(Object o) {
        return -1;
    }

    @Override
    public int findItemPosition(Object o) {
        return settings.indexOf(o);
    }


    static class ViewHolder {

        @InjectView(R.id.settingIcon)
        ImageView settingIcon;

        @InjectView(R.id.settingTitle)
        TextView settingTitle;

        @InjectView(R.id.currentValue)
        TextView currentValue;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
