package fb.sio.ecp.fr.federatedbirds.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;

/**
 * Created by Eric on 30/11/15.
 */
public class LoginTaskFragment extends DialogFragment {

    /**
     * To avoid typos, we use two constants declared as
     * public to be reused later on in the code
     */
    private static final String ARG_LOGIN = "login";
    private static final String ARG_PASSWORD = "password";


    public void setArguments(String login, String password) {
        Bundle args = new Bundle();
        args.putString(LoginTaskFragment.ARG_LOGIN, login);
        args.putString(LoginTaskFragment.ARG_PASSWORD, password);
        setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        AsyncTaskCompat.executeParallel(
                new LoginTaskFragment.LoginTask()
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.login_progress));
        return dialog;
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String login = getArguments().getString(ARG_LOGIN);
                String password = getArguments().getString(ARG_PASSWORD);
                return ApiClient.getInstance(getContext()).login(login,password);
            } catch (IOException e) {
                Log.e(LoginActivity.class.getSimpleName(), "Login Failed", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null){
                TokenManager.setUserToken(getContext(), token);
                getActivity().finish();
                startActivity(MainActivity.newIntent(getContext()));
            } else {
                Toast.makeText(getContext(), R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
