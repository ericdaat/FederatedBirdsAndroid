package fb.sio.ecp.fr.federatedbirds.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();
                    }
                });
    }

    private void login(){

         //get EditText Views from layout, they contain username and password
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        //extract String values from them
        String login = usernameText.getText().toString();
        String password = usernameText.getText().toString();

        //check both password and login to see if they match the specs
        if (!ValidationUtils.validateLogin(login)){
            usernameText.setError(getString(R.string.invalid_format));
            usernameText.requestFocus();
            return;
        }
        if (!ValidationUtils.validatePassword(password)){
            passwordText.setError(getString(R.string.invalid_password));
            passwordText.requestFocus();
            return;
        }

        /**
         * Then delegate the login procedure to a fragment.
         * We don't want to do this here because an Activity is subject to
         * being destroyed. Fragments are suited for such use.
         */
        LoginTaskFragment taskFragment = new LoginTaskFragment();
        taskFragment.setArguments(login,password);
        taskFragment.show(getSupportFragmentManager(),"login_task");
    }

}
