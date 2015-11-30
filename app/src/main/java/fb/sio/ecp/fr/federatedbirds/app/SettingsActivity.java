package fb.sio.ecp.fr.federatedbirds.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import fb.sio.ecp.fr.federatedbirds.R;


/**
 * Created by Eric on 30/11/15.
 */

public class SettingsActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            SettingsFragment fragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }

    }

}
