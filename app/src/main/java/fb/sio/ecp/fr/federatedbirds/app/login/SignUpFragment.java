package fb.sio.ecp.fr.federatedbirds.app.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fb.sio.ecp.fr.federatedbirds.ApiClient;
import fb.sio.ecp.fr.federatedbirds.R;
import fb.sio.ecp.fr.federatedbirds.utils.ValidationUtils;

/**
 * Created by Eric on 09/01/16.
 */
public class SignUpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void signup(){
        // Get form views
        EditText usernameText = (EditText) getView().findViewById(R.id.username);
        EditText passwordText = (EditText) getView().findViewById(R.id.password);
        EditText emailText = (EditText) getView().findViewById(R.id.email);

        String login = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString();

        if (!ValidationUtils.validateLogin(login)) {
            usernameText.setError(getString(R.string.invalid_format));
            usernameText.requestFocus();
            return;
        }

        if (!ValidationUtils.validatePassword(password)) {
            passwordText.setError(getString(R.string.invalid_format));
            passwordText.requestFocus();
            return;
        }

        if (!ValidationUtils.validateEmail(email)) {
            emailText.setError(getString(R.string.invalid_format));
            emailText.requestFocus();
            return;
        }



    }
}
