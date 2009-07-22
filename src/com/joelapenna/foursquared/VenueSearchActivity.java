/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquared;

import com.joelapenna.foursquare.types.Venue;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.widget.EditText;

/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class VenueSearchActivity extends ListActivity {
    private static final String TAG = "VenueSearchActivity";
    private static final boolean DEBUG = Foursquared.DEBUG;

    private SearchHandler mSearchHandler = new SearchHandler();
    private EditText mSearchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.venue_search_activity);

        setListAdapter(new VenueSearchListAdapter(this));

        mSearchEdit = (EditText)findViewById(R.id.searchEdit);
        mSearchEdit.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            sendQuery();
                            return true;
                    }
                }
                return false;
            }
        });
    }

    protected void sendQuery() {
        if (DEBUG) Log.d(TAG, "sendQuery()");
        Message msg = mSearchHandler.obtainMessage(SearchHandler.MESSAGE_QUERY_START);
        msg.sendToTarget();

    }

    protected void fireVenueActivityIntent(Venue venue) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // intent.putExtra("venue", value);
    }

    class SearchHandler extends Handler {

        public static final int MESSAGE_QUERY_START = 0;
        public static final int MESSAGE_QUERY_COMPLETE = 1;

        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MESSAGE_QUERY_START:
                    setProgressBarIndeterminateVisibility(true);
                    String query = mSearchEdit.getText().toString();
                    setTitle("Searching: " + query);
                    break;
                case MESSAGE_QUERY_COMPLETE:
                    setProgressBarIndeterminateVisibility(false);
                    break;
            }
        }
    }
}
