package org.bindings;

import java.io.IOException;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.webkit.JavascriptInterface;

public class AudioInterface {
    Context mContext;

    public AudioInterface(Context c) {
        mContext = c;
    }

    //Play an audio file from the webpage
    @JavascriptInterface
    public void playAudio(String aud) { //String aud - file name passed
        //from the JavaScript function
        final MediaPlayer mp;

        try {
            AssetFileDescriptor fileDescriptor =
                    mContext.getAssets().openFd(aud);
            mp = new MediaPlayer();
            mp.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            fileDescriptor.close();
            mp.prepare();
            mp.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
