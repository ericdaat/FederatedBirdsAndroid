package fb.sio.ecp.fr.federatedbirds.app;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;
import fb.sio.ecp.fr.federatedbirds.model.Message;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * If user doesn't have a token, he's not logged in so
         * he won't access the MainActivity right away. He
         * must log in first.
         */
        if (TokenManager.getUserToken(this) == null) {
            //starting LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            /**
             * finish() cleans the MainActivity from history
             * so the user can't go back there not logged in
             */
            finish();
        }
        /**
         * Default behaviour : the user will see activity_main layout
         */
        setContentView(R.layout.activity_main);

        /**
         * Attaching the navigation side bar, and listening to its events
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                /**
                 * switch to which item in the navigation bar is selected
                 * returning true acknowledges the triggering
                 */

                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.settings:
                        /**
                         * If the user clicks on settings, then launch the settings activity
                         */
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }

        });

        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        }
    }

    /**
     * Attaching the top bar since we modified our theme
     * and the toolbar is no longer loaded by default.
     */
    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                (DrawerLayout) findViewById(R.id.drawer),
                toolbar,
                R.string.open_menu,
                R.string.close_menu
        );
        mDrawerToggle.syncState();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

}