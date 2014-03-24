package com.malice.spritzglass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.glass.media.Sounds;
import com.malice.spritzglass.R;
import com.malice.spritzglass.core.BaseScrollActivity;
import com.malice.spritzglass.ui.adapters.RssCardAdapter;
import com.malice.spritzglass.ui.settings.cards.SpeedActivity;

import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

import static com.malice.spritzglass.ui.SpritzActivity.TAG_TEXT;

/**
 * @author Aenterhy.
 */
public class RssLoader extends BaseScrollActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleRss2Parser parser = new SimpleRss2Parser("http://www.forbes.com/real-time/feed2/",
                new SimpleRss2ParserCallback() {
                    @Override
                    public void onFeedParsed(List<RSSItem> items) {
                        for (RSSItem item : items)
                            item.setDescription(android.text.Html.fromHtml(item.getDescription()).toString());

                        audio.playSoundEffect(Sounds.SUCCESS);
                        vCardScroll.setAdapter(new RssCardAdapter(items));
                        vCardScroll.activate();
                        vCardScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                audio.playSoundEffect(Sounds.SELECTED);
                                openOptionsMenu();
                            }
                        });
                        hideProgress();
                    }

                    @Override
                    public void onError(Exception ex) {
                        showMessage(R.string.error, R.string.unable_to_upload, R.drawable.ic_warning_large, false);
                        hideProgress();
                        finish();
                    }
                }
        );
        parser.parseAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rss, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RSSItem article = (RSSItem) vCardScroll.getAdapter().getItem(vCardScroll.getSelectedItemPosition());
        switch (item.getItemId()) {
            case R.id.read:
                Intent spritz = new Intent(this, SpritzActivity.class);
                spritz.putExtra(TAG_TEXT, article.getDescription());
                startActivity(spritz);
                return true;

            case R.id.settings:
                startActivity(new Intent(this, SpeedActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public int getProgressIcon() {
        return R.drawable.ic_launcher;
    }

    @Override
    public int getProgressTitle() {
        return R.string.loading;
    }
}
