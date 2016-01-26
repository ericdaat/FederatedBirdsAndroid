package fb.sio.ecp.fr.federatedbirds.app;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.app.home.HomeFragment;
import fb.sio.ecp.fr.federatedbirds.app.login.LoginActivity;
import fb.sio.ecp.fr.federatedbirds.app.settings.SettingsActivity;
import fb.sio.ecp.fr.federatedbirds.app.users.FollowedFragment;
import fb.sio.ecp.fr.federatedbirds.app.users.FollowersFragment;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;

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

        checkUserLogin();

        /**
         * Default behaviour : the user will see activity_main layout
         */
        setContentView(R.layout.activity_main);

        /**
         * Attaching the navigation side bar, and listening to its events
         */
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                ((DrawerLayout) findViewById(R.id.drawer)).closeDrawers();
                /**
                 * switch to which item in the navigation bar is selected
                 * returning true acknowledges the triggering
                 */

                switch (item.getItemId()) {
                    case R.id.home:
                        Fragment fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, fragment)
                                .commit();
                        return true;
                    case R.id.followed:
                        fragment = new FollowedFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, fragment)
                                .commit();
                        return true;
                    case R.id.followers:
                        fragment = new FollowersFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, fragment)
                                .commit();
                        return true;
                    case R.id.settings:
                        //If the user clicks on settings, then launch the settings activity
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkUserLogin();
    }

    private void checkUserLogin() {
        if (TokenManager.getUserToken(this) == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}