package org.soundbounce.soundbounce;

import android.content.Intent;
import android.net.Uri;

import com.spotify.sdk.android.authentication.SpotifyAuthentication;
import com.spotify.sdk.android.playback.PlayConfig;

import org.xwalk.core.JavascriptInterface;

public class SpotifyBrowserAPIInterface {

    public SpotifyBrowserAPIInterface() {

    }

    protected String username;

    @JavascriptInterface
    public String getUsername()
    {
        return username;
    }

    @JavascriptInterface
    public String getVersion()
    {
        return "0.1";
    }

    @JavascriptInterface
    public void login(String username, String password)
    {
        this.username = username;

        MainActivity.getInstance().runOnUiThread(new Runnable()
        {
            public void run()
            {
                SpotifyAuthentication.openAuthWindow(MainActivity.CLIENT_ID, "token", MainActivity.REDIRECT_URI, new String[]{"user-read-private", "streaming"}, null, MainActivity.getInstance());
            }
        });
    }

    @JavascriptInterface
    public void openInSpotify(String trackId)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("spotify:track:" + trackId+"?action=browse"));
        MainActivity.getInstance().startActivity(browserIntent);
    }

    @JavascriptInterface
    public void pauseTrack()
    {
        MainActivity.getInstance().runOnUiThread(new Runnable()
        {
            public void run()
            {
                MainActivity.getInstance().getPlayer().pause();
            }
        });
    }

    // plays the given track at the given position in ms
    @JavascriptInterface
    public void playTrack(String trackId, int position)
    {
        final String trackURI = "spotify:track:" + trackId;

        final PlayConfig trackPositionPlayConfig = PlayConfig.createFor(trackURI);
        trackPositionPlayConfig.withInitialPosition(position);

        MainActivity.getInstance().runOnUiThread(new Runnable()
        {
            public void run()
            {
                MainActivity.getInstance().getPlayer().play(trackPositionPlayConfig);
            }
        });
    }

    @JavascriptInterface
    public void starTrack(String trackId)
    {
    }

    @JavascriptInterface
    public void openUrl(String url)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        MainActivity.getInstance().startActivity(browserIntent);
    }

}
