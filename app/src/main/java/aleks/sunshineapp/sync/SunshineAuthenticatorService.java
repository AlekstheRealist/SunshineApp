package aleks.sunshineapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Aleksreal7 on 12/22/16.
 */

public class SunshineAuthenticatorService extends Service {
    private SunshineAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new SunshineAuthenticator(this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
