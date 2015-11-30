package fb.sio.ecp.fr.federatedbirds.app;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import fb.sio.ecp.fr.federatedbirds.R;

/**
 * Created by Eric on 30/11/15.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
    }
}
