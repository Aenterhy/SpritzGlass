package com.malice.spritzglass.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.glass.widget.CardScrollAdapter;
import com.malice.spritzglass.R;
import com.malice.spritzglass.SpritzGlass;

import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Aenterhy.
 */
public class RssCardAdapter extends CardScrollAdapter {

    private final String TAG = RssCardAdapter.class.getSimpleName();

    private final List<RSSItem> items;

    public RssCardAdapter(List<RSSItem> items) {
        this.items = items;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(SpritzGlass.getInstance()).inflate(R.layout.rss_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        RSSItem item = items.get(i);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public int findIdPosition(Object o) {
        return -1;
    }

    @Override
    public int findItemPosition(Object o) {
        return items.indexOf(o);
    }

    static class ViewHolder {
        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.description)
        TextView description;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
