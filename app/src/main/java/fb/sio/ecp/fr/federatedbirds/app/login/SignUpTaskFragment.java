package fb.sio.ecp.fr.federatedbirds.app.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.app.MainActivity;
import fb.sio.ecp.fr.federatedbirds.auth.TokenManager;

/**
 * Created by Eric on 18/01/16.
 */
public class SignUpTaskFragment extends LoginTaskFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncTaskCompat.executeParallel(
                new SignUpTask()
        );
    }

    @Override
    public void setArguments(String login, String password, String email) {
        Bundle args = new Bundle();
        args.putString(LoginTaskFragment.ARG_LOGIN, login);
        args.putString(LoginTaskFragment.ARG_PASSWORD, password);
        args.putString(LoginTaskFragment.ARG_EMAIL, email);
        setArguments(args);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.signup_process));
        return dialog;
    }

    private class SignUpTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String login = getArguments().getString("login");
                String password = getArguments().getString("password");
                String email = getArguments().getString("email");
                return ApiClient.getInstance(getContext()).signup(login, password, email);
            } catch (IOException e) {
                Log.e(LoginActivity.class.getSimpleName(), "Sign Up Failed", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                TokenManager.setUserToken(getContext(), token);
                getActivity().finish();
                startActivity(MainActivity.newIntent(getContext()));
            } else {
                Toast.makeText(getContext(), R.string.creation_failed, Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    }
}
