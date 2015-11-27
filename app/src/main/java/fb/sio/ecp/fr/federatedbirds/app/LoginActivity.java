package fb.sio.ecp.fr.federatedbirds.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;
import fb.sio.ecp.fr.federatedbirds.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);

        String login = usernameText.getText().toString();
        String password = usernameText.getText().toString();

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

        new LoginTask(this).execute(login,password);
    }

    private static class LoginTask extends AsyncTask<String, Void, String>{

        Context mContext;

        public LoginTask(Context context){
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String login = params[0];
                String password = params[1];
                return ApiClient.getInstance(mContext).login(login,password);
            } catch (IOException e) {
                Log.e(LoginActivity.class.getSimpleName(),"Login Failed",e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null){
                TokenManager.setUserToken(mContext,token);
                mContext.startActivity(
                        new Intent(mContext,MainActivity.class)
                );
            } else {
                Toast.makeText(mContext,R.string.login_failed,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
