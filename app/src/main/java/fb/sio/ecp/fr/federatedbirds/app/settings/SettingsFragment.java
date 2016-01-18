package fb.sio.ecp.fr.federatedbirds.app.settings;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.app.MainActivity;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;

/**
 * Created by Eric on 30/11/15.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);

        findPreference("logout").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                TokenManager.clear(getContext());
                startActivity(MainActivity.newIntent(getContext()));
                return true;
            }
        });
    }


}
